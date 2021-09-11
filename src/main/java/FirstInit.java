import dao.HorseDao;
import dao.RaceDao;
import entity.Horse;
import utils.RaceUtils;

/**
 * Run before start server, to create data in your db
 */
public class FirstInit {
    public static void main(String[] args) throws InterruptedException {
        RaceDao raceDao = new RaceDao();
        HorseDao dao = new HorseDao();
        Horse horse;
        if (dao.readAll().size() < 2) {
            for (int i = 0; i < 10; i++) {
                horse = new Horse();
                horse.setName(String.valueOf(i));
                dao.create(horse);
            }
            RaceUtils.startNewRace(2);
        }
    }
}
