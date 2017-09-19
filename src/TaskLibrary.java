import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

/*Написать программу которая считывает с консоли число peopleCount и maxAmount.
Затем создает peopleCount потоков. Каждый поток - человек, хочет войти в библиотеку.
Библиотека имеет лимит кол-ва людей которые одновременно могут в ней находиться - maxAmount.
Каждый поток должен писать в консоль что он(человек) делает.
пришел ко входу в библиотеку
ждет входа в библиотеку (происходит только если нет места на момент прихода к библиотеке)
вошел в библиотеку
читает книгу (поток должен делать это действие рандомное кол-во времени от 1 до 5 секунд)
вышел из библиотеки
*/


/*Надо добавить в программу дверь - вход/выход в библиотеку.
В дверь может одновременно проходить 1 поток. Время прохождения через дверь потока = 0.5 секунды).
Появляются дополнительные действия со стороны поток которые они должны писать в консоль:
подошел к двери с улицы
подошел к двери изнутри
проходит через дверь внутрь
проходит через дверь наружу
прошел через дверь внутрь
прошел через дверь наружу
*/

public class TaskLibrary {

    public static void main(String[] args) throws InterruptedException {


        System.out.println("Введите число человек, которые зашли");
        Scanner sc = new Scanner(System.in);
        int peopleCount = sc.nextInt();

        System.out.println("Введите максимальное число человек, которые зайти в библиотеку");
        Scanner sc2 = new Scanner(System.in);
        int maxAmount = sc2.nextInt();

        Semaphore semaphore = new Semaphore(peopleCount);

        semaphore.acquire();
        new Thread(() -> System.out.println("Человек подошел к двери библиотеки")).start();
        semaphore.release();

        if (semaphore.availablePermits() ==0){

            System.out.println("Человек стоит у двери");
        }
        else {
            semaphore.acquire();
            System.out.println("Человек заходит в библиотеку через дверь внутрь");
            Thread.sleep(500);
            System.out.println("Человек прошел через дверь внутрь");
            semaphore.release();

        }

        for (int i = 0; i < peopleCount; i++) {
            final int x = i;
            semaphore.acquire();

            new Thread(() -> {

                System.out.println("Человек №" + x + " заходит в библиотеку");
            }).start();
            semaphore.release();
        }


        if(peopleCount < maxAmount) {
            new Thread(() -> System.out.println("Человек заходит в библиотеку, так как есть свободное место")).start();
        } else {
            new Thread(() -> System.out.println("Человек стоит у двери библиотеки, так как библиотека заполнена")).start();
        }

        //читает книгу (поток должен делать это действие рандомное кол-во времени от 1 до 5 секунд)

        new Thread(() -> {

            Random r = new Random();
            int millisecondsToSleep = r.nextInt(4000) + 1000;
            try {
                semaphore.acquire();
                System.out.printf("Человек читает книгу " + millisecondsToSleep + " миллисекунд\n");

                Thread.sleep(millisecondsToSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } semaphore.release();
        }).start();


        new Thread(() -> System.out.println("Человек вышел из библиотеки")).start();

    }
}
