package com.game.tournament.gametournament.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.tournament.gametournament.model.Games;
import com.game.tournament.gametournament.model.TournamentPrice;
import com.game.tournament.gametournament.model.Tournaments;
import com.game.tournament.gametournament.repository.GameRepo;
import com.game.tournament.gametournament.repository.TournamentPriceRepo;
import com.game.tournament.gametournament.repository.TournamentRepo;
import com.game.tournament.gametournament.utils.DataTypeUtility;
import com.game.tournament.gametournament.utils.MobileResponseDTOFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GameService {

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private TournamentPriceRepo tournamentPriceRepo;

    @Autowired
    private MobileResponseDTOFactory mobileResponseDTOFactory;

    @Autowired
    private TournamentRepo tournamentRepo;


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

        String imageDataJson = "";
        try {
            Map<String, Object> imageDataMap = new HashMap<>();
            if(file !=null){
            imageDataMap.put("filename", file.getOriginalFilename());
            imageDataMap.put("type", file.getContentType());
            imageDataMap.put("size", file.getSize());
            imageDataMap.put("orignalfilename", file.getOriginalFilename());
            ObjectMapper objectMapper = new ObjectMapper();
            imageDataJson = objectMapper.writeValueAsString(imageDataMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(id != null && id >0){
            boolean present = tournamentRepo.findById(id).isPresent();
            if(present){
                Tournaments tournaments = tournamentRepo.findById(id).get();
                tournaments.setName(name);
                tournaments.setPriceid(price);
                tournaments.setGameid(game);
                tournaments.setDescription(description);
                tournaments.setDate(date);
                tournaments.setAttachment(imageDataJson);
                tournaments.setStarttime(starttime);
                tournaments.setEndtime(endtime);
                tournaments.setMinimumplayer(minimum_player);
                tournaments.setMaximumplayer(maximum_player);
                tournaments.setSecretcode(secret_code);
                tournamentRepo.save(tournaments);
                return mobileResponseDTOFactory.successMessage("Successfully Save");
            }
        } else {
            Tournaments tournaments = new Tournaments();
            tournaments.setName(name);
            tournaments.setPriceid(price);
            tournaments.setGameid(game);
            tournaments.setDescription(description);
            tournaments.setDate(date);
            tournaments.setAttachment(imageDataJson);
            tournaments.setStarttime(starttime);
            tournaments.setEndtime(endtime);
            tournaments.setMinimumplayer(minimum_player);
            tournaments.setMaximumplayer(maximum_player);
            tournaments.setSecretcode(secret_code);
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
                if(isdeleted==null || !isdeleted){
                    resultMap.put("tournament",tournaments);
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
                    String price = DataTypeUtility.stringValue(price_map.get(tournaments.getPriceid()));
                    tournament_map.put("id",id1);
                    tournament_map.put("name",name);
                    tournament_map.put("price",price);
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

    public Object getTournamentsForUser(Map<String,Object> param) throws NoSuchFieldException, IllegalAccessException {
        Map<String,Object> resultMap = new HashMap<>();
        List<Tournaments> tournamentsList = tournamentRepo.getTournaments();
        List<Map<String ,Object>> tournament_list = new ArrayList<>();
        Map<Long, Object> price_map = DataTypeUtility.getIdFieldMap(tournamentPriceRepo, "price");
        Map<Long, Object> game_map = DataTypeUtility.getIdFieldMap(gameRepo, "name");
        if(tournamentsList != null && tournamentsList.size()>0){
            for (Tournaments obj : tournamentsList) {
                Map<String,Object> tournament_map = new HashMap<>();
                tournament_map.put("name",obj.getName());
                tournament_map.put("date",obj.getDate());
                tournament_map.put("description",obj.getDescription());
                tournament_map.put("endtime",obj.getEndtime());
                tournament_map.put("maximumplayer",obj.getMaximumplayer());
                tournament_map.put("minimumplayer",obj.getMinimumplayer());
                tournament_map.put("starttime",obj.getStarttime());
                tournament_map.put("secretcode",obj.getSecretcode());
                tournament_map.put("id",obj.getId());
                tournament_map.put("attachment",obj.getAttachment());
                tournament_map.put("price",price_map.get(obj.getPriceid()));
                tournament_map.put("game",game_map.get(obj.getGameid()));

                tournament_list.add(tournament_map);
            }
        }

        resultMap.put("list",tournament_list);
        return resultMap;
    }

}
