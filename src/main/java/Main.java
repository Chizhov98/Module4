import dao.HorseDao;
import entity.Horse;
import entity.Race;
import utils.RaceUtils;

public class Main {
    public static void main(String[] args) {
        HorseDao dao = new HorseDao();
        Horse horse;
        for(int i =0;i<10;i++){
            horse = new Horse();
            horse.setName(String.valueOf(i));
            dao.create(horse);
        }
        RaceUtils.startNewRace(2);
    }
}
