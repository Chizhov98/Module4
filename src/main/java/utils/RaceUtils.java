package utils;

import dao.HorseDao;
import dao.RaceDao;
import dao.RaceListDao;
import entity.Horse;
import tdo.ProfileInfo;
import entity.Race;
import entity.RaceList;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.time.LocalDate;

public class RaceUtils {
    private static final HorseDao horseDao = new HorseDao();
    private static final RaceDao raceDao = new RaceDao();
    private static final RaceListDao raceListDao = new RaceListDao();
    private static int horseCount;
    private static final Random random = new Random();

    private static final int DISTANCE = 1000;
    private static final int MIN_STEP = 100;
    private static final int STEP_RANGE = 100;
    private static final int MIN_SLEEP_TIME = 400;
    private static final int SLEEP_RANGE = 100;

    public static boolean startNewRace(int chosenHorseId) throws InterruptedException {
        Horse chosenHorse = horseDao.read(chosenHorseId);
        Race race = new Race();
        race.setDate(LocalDate.now());
        List<Horse> horseList = horseDao.readAll();
        horseCount = horseList.size();
        List<Horse> results = new ArrayList<>();
        results = startHorseRun(horseList, results);
        raceDao.create(race);
        saveRunData(race, results, chosenHorse);
        return true;
    }

    private static void saveRunData(Race race, List<Horse> results, Horse chosen) {
        int horsePos = 0;
        RaceList list;
        for (Horse horse : results) {
            list = new RaceList();
            list.setHorse(horse);
            list.setChosen(horse.getId() == chosen.getId());
            list.setPosition(++horsePos);
            list.setRace(race);
            raceListDao.create(list);
        }
    }

    private static List<Horse> startHorseRun(List<Horse> horses, List<Horse> results) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(horses.size());
        Runnable r = () -> {
            Horse horse = horses.get(--horseCount);
            boolean isFinished = false;
            int distanceLeft = DISTANCE;
            while (!isFinished) {
                distanceLeft -= random.nextInt(STEP_RANGE) + MIN_STEP;
                if (distanceLeft > 0) {
                    try {
                        Thread.sleep(random.nextInt(SLEEP_RANGE) + MIN_SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    results.add(horse);
                    isFinished = true;
                }
            }
            latch.countDown();
        };
        for (int i = 0; i < horses.size(); i++) {
            executor.execute(r);
        }
        while (results.size() != horses.size()) {
            Thread.sleep(SLEEP_RANGE);
        }
        return results;
    }

    public static List<RaceList> getRaceInfo(int id) {
        List<RaceList> list = raceListDao.getRaceInfo(getRace(id));
        list.sort(Comparator.comparingInt(RaceList::getPosition));
        return list;
    }

    public static Race getRace(int id) {
        return raceDao.read(id);
    }

    public static ProfileInfo getStats() {
        int races = raceDao.getRaceCount();
        int winRate = raceListDao.getWinRate();
        return new ProfileInfo(races, winRate);
    }
    public static int[] getAllHorsesId(){
        return horseDao.readAll().stream().mapToInt(Horse::getId).toArray();
    }
}
