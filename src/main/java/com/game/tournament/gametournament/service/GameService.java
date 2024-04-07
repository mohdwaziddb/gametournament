package com.game.tournament.gametournament.service;

import com.game.tournament.gametournament.model.*;
import com.game.tournament.gametournament.repository.*;
import com.game.tournament.gametournament.utils.DataTypeUtility;
import com.game.tournament.gametournament.utils.MobileResponseDTOFactory;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class GameService {

    private static String Key = "rzp_test_x2FdmAMnovVzvG";
    private static String Key_Secret = "4t7mqBhnN3X2Ronlgc59Ql8Q";
    private static String Currecny = "INR";

    @Value("${image.upload.dir}")
    private String imageUploadDir;

    private static final String imageUploadDirTournament = "src/main/resources/static/images/tournament/";

    private static final String getimageUploadDirTournament = "/images/tournament/";

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private TournamentPriceRepo tournamentPriceRepo;

    @Autowired
    private MobileResponseDTOFactory mobileResponseDTOFactory;

    @Autowired
    private TournamentRepo tournamentRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public ResponseEntity<?> createGame(Map<String , Object> param){
        String name = DataTypeUtility.stringValue(param.get("name"));
        String description = DataTypeUtility.stringValue(param.get("description"));
        Long id = DataTypeUtility.longValue(param.get("id"));
        if(id != null && id >0){
            boolean present = gameRepo.findById(id).isPresent();
            if(present){
                Games games = gameRepo.findById(id).get();
                games.setName(name);
                games.setDescription(description);

                gameRepo.save(games);
                return mobileResponseDTOFactory.successMessage("Successfully Save");
            }
        } else {
            if(name==null || name.equals("")){
                return mobileResponseDTOFactory.failedMessage("Name Cannot be blank");
            }

            Games games = new Games();
            games.setName(name);
            games.setDescription(description);
            games.setCreatedon(DataTypeUtility.getCurrentDateTimeInIndianFormatUI());
            gameRepo.save(games);
            return mobileResponseDTOFactory.successMessage("Successfully Save");
        }

        return mobileResponseDTOFactory.failedMessage("Cannot Save");

    }

    public Object gamesList(Map<String,Object> param){
        Map<String,Object> resultMap = new HashMap<>();
        Long id = DataTypeUtility.longValue(param.get("id"));
        if(id!=null && id>0){
            Games games = gameRepo.findById(id).get();
            resultMap.put("games_list",games);
        } else {
            List<Games> games_list = gameRepo.findAllByIsdeletedIsFalseOrIsdeletedIsNullOrderByIdDesc();
            resultMap.put("games_list",games_list);
        }
        return resultMap;
    }

    @Transactional
    public ResponseEntity<?> deleteGame(Map<String , Object> param){
        Long id = DataTypeUtility.longValue(param.get("id"));
        if(id!=null && id>0){
            boolean present = gameRepo.findById(id).isPresent();
            if(present){
                Games games = gameRepo.findById(id).get();
                games.setIsdeleted(true);
                gameRepo.save(games);
                return mobileResponseDTOFactory.successMessage("Successfully Delete");
            }
        }
        return mobileResponseDTOFactory.failedMessage("Cannot Delete");
    }

    @Transactional
    public ResponseEntity<?> createPrice(Map<String , Object> param){
        Long price = DataTypeUtility.longValue(param.get("price"));
        Long id = DataTypeUtility.longValue(param.get("id"));
        if(id != null && id >0){
            boolean present = tournamentPriceRepo.findById(id).isPresent();
            if(present){
                TournamentPrice tournamentPrice = tournamentPriceRepo.findById(id).get();
                tournamentPrice.setPrice(price);

                tournamentPriceRepo.save(tournamentPrice);
                return mobileResponseDTOFactory.successMessage("Successfully Save");
            }
        } else {
            if(price==null || price.equals("")){
                return mobileResponseDTOFactory.failedMessage("Price Cannot be blank");
            }

            TournamentPrice tournamentPrice = new TournamentPrice();
            tournamentPrice.setPrice(price);
            tournamentPriceRepo.save(tournamentPrice);
            return mobileResponseDTOFactory.successMessage("Successfully Save");
        }

        return mobileResponseDTOFactory.failedMessage("Cannot Save");

    }

    public Object priceList(Map<String,Object> param){
        Map<String,Object> resultMap = new HashMap<>();
        Long id = DataTypeUtility.longValue(param.get("id"));
        if(id!=null && id>0){
            TournamentPrice tournamentPrice = tournamentPriceRepo.findById(id).get();
            resultMap.put("price_list",tournamentPrice);
        } else {
            List<TournamentPrice> price_list = tournamentPriceRepo.findByOrderByIdDesc();
            resultMap.put("price_list",price_list);
        }
        return resultMap;
    }

    @Transactional
    public ResponseEntity<?> deletePrice(Map<String , Object> param){
        Long id = DataTypeUtility.longValue(param.get("id"));
        if(id!=null && id>0){
            boolean present = tournamentPriceRepo.findById(id).isPresent();
            if(present){
                TournamentPrice tournamentPrice = tournamentPriceRepo.findById(id).get();
                tournamentPriceRepo.delete(tournamentPrice);
                return mobileResponseDTOFactory.successMessage("Successfully Delete");
            }
        }
        return mobileResponseDTOFactory.failedMessage("Cannot Delete");
    }

    @Transactional
    public ResponseEntity<?> createTournament(MultipartFile file, Map<String , Object> param) throws IOException {
        Long id = DataTypeUtility.longValue(param.get("id"));
        String name = DataTypeUtility.stringValue(param.get("name"));
        Long price = DataTypeUtility.longValue(param.get("price"));
        Long game = DataTypeUtility.longValue(param.get("game"));
        String description = DataTypeUtility.stringValue(param.get("description"));
        String date = DataTypeUtility.stringValue(param.get("date"));
        String starttime = DataTypeUtility.stringValue(param.get("starttime"));
        String endtime = DataTypeUtility.stringValue(param.get("endtime"));
        Long minimum_player = DataTypeUtility.longValue(param.get("minimum_player"));
        Long maximum_player = DataTypeUtility.longValue(param.get("maximum_player"));
        String secret_code = DataTypeUtility.stringValue(param.get("secret_code"));
        Boolean is_completed = DataTypeUtility.booleanValue(param.get("is_completed"));
        Boolean isimagechange = DataTypeUtility.booleanValue(param.get("isimagechange"));
        boolean isStartTimeBeforeEndTime = false;
        if(starttime != null && !starttime.equals("") && endtime != null && !endtime.equals("")) {
            isStartTimeBeforeEndTime = compareTimes(starttime, endtime);
        }

        //boolean isDateAfterTwoDays = isDateWithinRange(date);



        if(isStartTimeBeforeEndTime){
            return mobileResponseDTOFactory.failedMessage("Start Time before End Time");
        }

        if(name == null || name.equals("")){
            mobileResponseDTOFactory.failedMessage("Name cannot be Blank");
        }
        if(price == null || price.equals("") || price< 0){
            mobileResponseDTOFactory.failedMessage("Price cannot be Blank");
        }
        if(game == null || game.equals("") || game< 0){
            mobileResponseDTOFactory.failedMessage("Game cannot be Blank");
        }
        if(description == null || description.equals("")){
            mobileResponseDTOFactory.failedMessage("Description cannot be Blank");
        }
        if(date == null || date.equals("")){
            mobileResponseDTOFactory.failedMessage("Date cannot be Blank");
        }
        if(starttime == null || starttime.equals("")){
            mobileResponseDTOFactory.failedMessage("Start Time cannot be Blank");
        }
        if(endtime == null || endtime.equals("")){
            mobileResponseDTOFactory.failedMessage("End Time cannot be Blank");
        }
        if(minimum_player == null || minimum_player.equals("")){
            mobileResponseDTOFactory.failedMessage("Minimum Player cannot be Blank");
        }
        if(maximum_player == null || maximum_player.equals("")){
            mobileResponseDTOFactory.failedMessage("Maximum Player cannot be Blank");
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String fileName = "";
        if(file != null && !file.isEmpty()) {
            fileName = timestamp + "__" + file.getOriginalFilename();
            try {
                Path uploadPath = Paths.get(ResourceUtils.getURL(imageUploadDirTournament).toURI());
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Files.copy(file.getInputStream(), uploadPath.resolve(fileName));
            } catch (Exception e) {
                e.printStackTrace();
                //return mobileResponseDTOFactory.failedMessage("Failed to save image");
            }
        }


        if(id != null && id >0){
            boolean present = tournamentRepo.findById(id).isPresent();
            if(present){
                Tournaments tournaments = tournamentRepo.findById(id).get();
                Boolean isdeleted = tournaments.getIsdeleted();
                Boolean iscompleted = tournaments.getIscompleted();
                if(isdeleted==null || !isdeleted){
                    if(iscompleted==null || !iscompleted){
                    tournaments.setName(name);
                    tournaments.setPriceid(price);
                    tournaments.setGameid(game);
                    tournaments.setDescription(description);
                    tournaments.setDate(date);

                    if(isimagechange){
                        tournaments.setAttachment(fileName);
                    } else {
                        tournaments.setAttachment(tournaments.getAttachment());
                    }

                    tournaments.setStarttime(starttime);
                    tournaments.setEndtime(endtime);
                    tournaments.setMinimumplayer(minimum_player);
                    tournaments.setMaximumplayer(maximum_player);
                    tournaments.setSecretcode(secret_code);
                    tournaments.setIscompleted(is_completed);
                    tournamentRepo.save(tournaments);
                    return mobileResponseDTOFactory.successMessage("Successfully Save");
                    }
                }
            }
        } else {
            Tournaments tournaments = new Tournaments();
            tournaments.setName(name);
            tournaments.setPriceid(price);
            tournaments.setGameid(game);
            tournaments.setDescription(description);
            tournaments.setDate(date);
            tournaments.setAttachment(fileName);
            tournaments.setStarttime(starttime);
            tournaments.setEndtime(endtime);
            tournaments.setMinimumplayer(minimum_player);
            tournaments.setMaximumplayer(maximum_player);
            tournaments.setSecretcode(secret_code);
            tournaments.setIscompleted(is_completed);
            tournaments.setCreatedon(DataTypeUtility.getCurrentDateTimeInIndianFormatUI());
            tournamentRepo.save(tournaments);
            return mobileResponseDTOFactory.successMessage("Successfully Save");
        }

        return mobileResponseDTOFactory.failedMessage("Cannot Save");

    }


    public Object getTournaments(Map<String,Object> param) throws NoSuchFieldException, IllegalAccessException {
        Map<String,Object> resultMap = new HashMap<>();
        Long id = DataTypeUtility.longValue(param.get("id"));
        Map<Long, Object> price_map = DataTypeUtility.getIdFieldMap(tournamentPriceRepo, "price");
        if(id != null && id>0){
            boolean present = tournamentRepo.findById(id).isPresent();
            if(present){
                Tournaments tournaments = tournamentRepo.findById(id).get();
                Boolean isdeleted = tournaments.getIsdeleted();
                Boolean iscompleted = tournaments.getIscompleted();
                if(isdeleted==null || !isdeleted){
                    if(iscompleted==null || !iscompleted) {
                        resultMap.put("tournament", tournaments);
                    }
                }
            }
        } else {
            List<Tournaments> tournament_list = tournamentRepo.findAllByIsdeletedIsFalseOrIsdeletedIsNullOrderByIdDesc();
            List<Map<String,Object>> tournament_list_new = new ArrayList<>();
            if(tournament_list != null && tournament_list.size()>0){
                for (Tournaments tournaments : tournament_list) {
                    Map<String,Object> tournament_map = new HashMap<>();
                    Long id1 = tournaments.getId();
                    String name = tournaments.getName();
                    Boolean iscompleted = tournaments.getIscompleted();
                    String price = DataTypeUtility.stringValue(price_map.get(tournaments.getPriceid()));
                    tournament_map.put("id",id1);
                    tournament_map.put("name",name);
                    tournament_map.put("price",price);
                    tournament_map.put("iscompleted",iscompleted);
                    tournament_list_new.add(tournament_map);
                }
            }
            resultMap.put("tournament_list",tournament_list_new);
        }
        List<Games> games_list = gameRepo.findAllByIsdeletedIsFalseOrIsdeletedIsNullOrderByIdDesc();
        List<TournamentPrice> price_list = tournamentPriceRepo.findByOrderByIdDesc();

        resultMap.put("games_list",games_list);
        resultMap.put("price_list",price_list);

        return resultMap;
    }

    @Transactional
    public ResponseEntity<?> deleteTournament(Map<String , Object> param){
        Long id = DataTypeUtility.longValue(param.get("id"));
        if(id!=null && id>0){
            boolean present = tournamentRepo.findById(id).isPresent();
            if(present){
                Tournaments tournaments = tournamentRepo.findById(id).get();
                tournaments.setIsdeleted(true);
                tournamentRepo.save(tournaments);
                return mobileResponseDTOFactory.successMessage("Successfully Delete");
            }
        }
        return mobileResponseDTOFactory.failedMessage("Cannot Delete");
    }

    public static boolean compareTimes(String startTime, String endTime) {
        // Parse start time
        String[] startTokens = startTime.split(":");
        int startHours = Integer.parseInt(startTokens[0]);
        int startMinutes = Integer.parseInt(startTokens[1]);
        int totalStartMinutes = startHours * 60 + startMinutes;

        // Parse end time
        String[] endTokens = endTime.split(":");
        int endHours = Integer.parseInt(endTokens[0]);
        int endMinutes = Integer.parseInt(endTokens[1]);
        int totalEndMinutes = endHours * 60 + endMinutes;

        // Compare start and end times
        return totalStartMinutes > totalEndMinutes;
    }

    public static boolean isDateWithinRange(String inputDate) {
        // Create SimpleDateFormat with pattern "yyyy-MM-dd"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date(); // Get current date

        // Parse input date string
        Date date;
        try {
            date = sdf.parse(inputDate);
        } catch (ParseException e) {
            // Handle parsing exception
            e.printStackTrace();
            return false;
        }

        // Get the date for tomorrow
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Date tomorrowDate = cal.getTime();

        // Check if input date falls within the range of today and tomorrow
        return !date.before(currentDate) && !date.after(tomorrowDate);
    }

    public Object getTournamentsForUser(Map<String,Object> param, HttpServletRequest request) throws Exception {
        Map<String,Object> resultMap = new HashMap<>();
        Long id = DataTypeUtility.longValue(param.get("id"));
        Map<Long, Object> price_map = DataTypeUtility.getIdFieldMap(tournamentPriceRepo, "price");
        Map<Long, Object> game_map = DataTypeUtility.getIdFieldMap(gameRepo, "name");
        Long currentUserId = mobileResponseDTOFactory.getCurrentUserId(request);

        List<Players> all_join_tournament_list_byuserid = playerRepo.findAllByUserid(currentUserId);
        HashSet<Long> join_tournament_set = new HashSet<>();
        if(all_join_tournament_list_byuserid != null && all_join_tournament_list_byuserid.size()>0){
            for (Players obj : all_join_tournament_list_byuserid) {
                join_tournament_set.add(obj.getTounamentid());
            }
        }

        /*if(id != null && id>0){
            boolean present = tournamentRepo.findById(id).isPresent();
            if(present){
                Tournaments tournaments = tournamentRepo.findById(id).get();
                Boolean isdeleted = tournaments.getIsdeleted();
                Boolean iscompleted = tournaments.getIscompleted();
                if(isdeleted==null || !isdeleted){
                    if(iscompleted==null || !iscompleted){
                        Long gameid = tournaments.getGameid();
                        Long priceid = tournaments.getPriceid();
                        Long id1 = tournaments.getId();
                        if(join_tournament_set.contains(id1)){
                            tournaments.setIsjoin(true);
                        } else {
                            tournaments.setIsjoin(false);
                        }
                        tournaments.setPrice(DataTypeUtility.stringValue(price_map.get(priceid)));
                        tournaments.setGame(DataTypeUtility.stringValue(game_map.get(gameid)));
                        resultMap.put("tournament",tournaments);
                    }
                }
            }
        } else {*/
        if(currentUserId!= null && !currentUserId.equals("")){
            List<Tournaments> tournamentsList = tournamentRepo.getTournaments();
            List<Map<String ,Object>> tournament_list = new ArrayList<>();
            if(tournamentsList != null && tournamentsList.size()>0) {
                for (Tournaments obj : tournamentsList) {
                    Map<String, Object> tournament_map = new HashMap<>();
                    Long objId = obj.getId();
                    tournament_map.put("name", obj.getName());
                    tournament_map.put("date", obj.getDate());
                    tournament_map.put("description", obj.getDescription());
                    tournament_map.put("endtime", obj.getEndtime());
                    tournament_map.put("maximumplayer", obj.getMaximumplayer());
                    tournament_map.put("minimumplayer", obj.getMinimumplayer());
                    tournament_map.put("starttime", obj.getStarttime());
                    tournament_map.put("secretcode", obj.getSecretcode());
                    tournament_map.put("id", objId);
                    String attachment = obj.getAttachment();
                    String imageUrl = "";
                    if(attachment != null && !attachment.equals("")){
                        imageUrl = getimageUploadDirTournament + attachment;
                    }

                    tournament_map.put("attachment", imageUrl);
                    tournament_map.put("price", price_map.get(obj.getPriceid()));
                    tournament_map.put("game", game_map.get(obj.getGameid()));
                    if (join_tournament_set.contains(objId)) {
                        tournament_map.put("isjoin", true);
                    } else {
                        tournament_map.put("isjoin", false);
                    }


                    tournament_list.add(tournament_map);
                }
            }
           // }

            resultMap.put("list",tournament_list);
            resultMap.put("userid",currentUserId);
        }

        return resultMap;
    }

    @Transactional
    public ResponseEntity<?> joiningTournament(Map<String , Object> param, HttpServletRequest request){
        Long id = DataTypeUtility.longValue(param.get("id"));
        String transactionid = DataTypeUtility.stringValue(param.get("transactionid"));
        Long userid = DataTypeUtility.longValue(param.get("userid"));
        Long currentUserId = mobileResponseDTOFactory.getCurrentUserId(request);
        String currentDateTimeInIndianFormat = DataTypeUtility.getCurrentDateTimeInIndianFormatUI();


        Players players = new Players();
        players.setTounamentid(id);
        if(userid.equals(currentUserId)){
            players.setUserid(currentUserId);
        }
        players.setCreatedon(currentDateTimeInIndianFormat);
        players.setTransactionid(transactionid);
        playerRepo.save(players);
        return mobileResponseDTOFactory.successMessage("Successfully Save");

    }

    public Object createTransaction(Map<String ,Object> param, HttpServletRequest request) {
        Double amount = DataTypeUtility.doubleZeroValue(param.get("amount"));
        try {
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100);
            orderRequest.put("currency", Currecny);

            RazorpayClient razorpayClient = new RazorpayClient(Key,Key_Secret);
            Order order = razorpayClient.orders.create(orderRequest);
            TransactionalDetails transactionalDetails = prepareTransactionalDetails(order,request);
            return transactionalDetails;
        } catch (Exception e){
            System.out.println(e.getMessage());;
            return null;
        }
    }

    private TransactionalDetails prepareTransactionalDetails(Order order,HttpServletRequest request) throws Exception{
        String orderid = DataTypeUtility.stringValue(order.get("id"));
        String currency = DataTypeUtility.stringValue(order.get("currency"));
        Integer amount = DataTypeUtility.integerValue(order.get("amount"));
        Long currentUserId = mobileResponseDTOFactory.getCurrentUserId(request);
        List<Users> user_list = userRepository.findAll();
        Map<Long,Users> user_map = new HashMap<>();
        if(user_list!=null && user_list.size()>0){
            for (Users modal : user_list) {
                user_map.put(modal.getId(),modal);
            }
        }
        String username = user_map.get(currentUserId).getUsername();
        String mobileno = user_map.get(currentUserId).getMobileno();
        String emailid = user_map.get(currentUserId).getEmailid();

        TransactionalDetails transactionalDetails = new TransactionalDetails(orderid,amount,currency,Key,Key_Secret,username,mobileno,emailid);
        return transactionalDetails;
    }

    public Object getPlayerDetails(Map<String,Object> param, HttpServletRequest request) throws Exception {
        Map<String,Object> resultMap = new HashMap<>();
        List<Players> list = playerRepo.findAllByOrderByIdDesc();
        List<Tournaments> tournament_list = tournamentRepo.findAllByIsdeletedIsFalseOrIsdeletedIsNullOrderByIdDesc();
        Map<Long,Tournaments> player_map = new HashMap<>();
        if(tournament_list!=null && tournament_list.size()>0){
            for (Tournaments modal : tournament_list) {
                player_map.put(modal.getId(),modal);
            }
        }

        Map<Long, Object> username_map = DataTypeUtility.getIdFieldMap(userRepository, "username");
        Map<Long, Object> price_map = DataTypeUtility.getIdFieldMap(tournamentPriceRepo, "price");
        Map<Long, Object> game_map = DataTypeUtility.getIdFieldMap(gameRepo, "name");

        List<Map<String,Object>> new_list = new ArrayList<>();
        if(list!= null && list.size()>0){
            for (Players obj : list) {
                Map<String,Object> map = new HashMap<>();
                Long userid = obj.getUserid();
                Long tounamentid = obj.getTounamentid();
                Long priceid = player_map.get(tounamentid).getPriceid();
                Long gameid = player_map.get(tounamentid).getGameid();

                map.put("name",username_map.get(userid));
                map.put("game",game_map.get(gameid));
                map.put("amount",price_map.get(priceid));
                map.put("tournament",player_map.get(tounamentid).getName());
                new_list.add(map);
            }
        }

        resultMap.put("list",new_list);
        return resultMap;
    }

    public Object getUserDetailsById(Map<String,Object> param, HttpServletRequest request) throws Exception {
        HashMap<String ,Object> resultMap = new HashMap<>();
        Long userid = DataTypeUtility.longValue(param.get("userid"));
        boolean present = userRepository.findAllById(userid).isPresent();
        String currentUserName = mobileResponseDTOFactory.getCurrentUserName(request);
        if(present){
            Users usermodal = userRepository.findAllById(userid).get();
            String username = usermodal.getUsername();
            String firstname = usermodal.getFirstname();
            String lastname = usermodal.getLastname();
            String mobileno = usermodal.getMobileno();
            String emailid = usermodal.getEmailid();
            if(currentUserName.equalsIgnoreCase(username)){
                resultMap.put("firstname",firstname);
                resultMap.put("lastname",lastname);
                resultMap.put("mobileno",mobileno);
                resultMap.put("emailid",emailid);
                resultMap.put("username",username);
            }
        }
        return resultMap;
    }

    @Transactional
    public ResponseEntity<?> saveUserDetailsById(Map<String , Object> param,HttpServletRequest request){
        Long userid = DataTypeUtility.longValue(param.get("userid"));
        String firstname = DataTypeUtility.stringValue(param.get("firstname"));
        String lastname = DataTypeUtility.stringValue(param.get("lastname"));
        String emailid = DataTypeUtility.stringValue(param.get("emailid"));
        String phoneno = DataTypeUtility.stringValue(param.get("phoneno"));
        boolean present = userRepository.findAllById(userid).isPresent();
        String currentUserName = mobileResponseDTOFactory.getCurrentUserName(request);
        if(present){
            Users usermodal = userRepository.findAllById(userid).get();
            String username = usermodal.getUsername();
            if(username.equalsIgnoreCase(currentUserName)){
                usermodal.setFirstname(firstname);
                usermodal.setLastname(lastname);
                usermodal.setEmailid(emailid);
                usermodal.setMobileno(phoneno);
                userRepository.save(usermodal);
                return mobileResponseDTOFactory.successMessage("Profile Update Successfully");
            }
        }

        return mobileResponseDTOFactory.failedMessage("Cannot Save");
    }

    public Object getTournamentAndUserDetails(Map<String,Object> param, HttpServletRequest request) throws Exception {
        HashMap<String ,Object> resultMap = new HashMap<>();
        Map<Long, Object> usernamemap = DataTypeUtility.getIdFieldMap(userRepository, "username");


        List<Tournaments> tournament_list = tournamentRepo.findAllByIsdeletedIsFalseOrIsdeletedIsNullOrderByIdDesc();
        Map<Long, Object> pricemap = DataTypeUtility.getIdFieldMap(tournamentPriceRepo, "price");
        List<Map<String ,Object>> tournamenprice_new = new ArrayList<>();
        for (Tournaments tournaments : tournament_list) {
            Map<String ,Object> datamap = new HashMap<>();
            if(tournaments.getIscompleted() != null){
                if(tournaments.getIscompleted())
                continue;
            }
            Long id = tournaments.getId();
            String name = tournaments.getName();
            String price = DataTypeUtility.stringValue(pricemap.get(tournaments.getPriceid()));
            datamap.put("id",id);
            datamap.put("name",name);
            datamap.put("price",price);
            tournamenprice_new.add(datamap);
        }
        resultMap.put("users",usernamemap);
        resultMap.put("tournaments",tournamenprice_new);
        return resultMap;
    }



}
