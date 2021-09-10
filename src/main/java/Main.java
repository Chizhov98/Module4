import dao.HorseDao;
import entity.Horse;
import utils.RaceUtils;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        HorseDao dao = new HorseDao();
        Horse horse;
        if(dao.readAll().size()<2) {
            for (int i = 0; i < 10; i++) {
                horse = new Horse();
                horse.setName(String.valueOf(i));
                dao.create(horse);
            }
        }
        RaceUtils.startNewRace(2);
    }
}
