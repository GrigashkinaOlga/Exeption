// Напишите приложение, которое будет запрашивать у пользователя следующие данные, разделенные пробелом:

// Фамилия Имя Отчество дата _ рождения номер _ телефона пол

// Форматы данных:

// фамилия, имя, отчество - строки
// дата _ рождения - строка формата dd.mm.yyyy
// номер _ телефона - целое беззнаковое число без форматирования
// пол - символ латиницей f или m.

// Приложение должно проверить введенные данные по количеству. 
// Если количество не совпадает, вернуть код ошибки, обработать его и показать пользователю сообщение, 
// что он ввел меньше и больше данных, чем требуется.

// Приложение должно распарсить полученную строку и выделить из них требуемые значения. 
// Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. 
// Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано, 
// пользователю выведено сообщение с информацией, что именно неверно.

// Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, 
// в него в одну строку должны записаться полученные данные, вида
// <Фамилия> <Имя> <Отчество> <дата _ рождения> <номер _ телефона> <пол>

// Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
// Не забудьте закрыть соединение с файлом.
// При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, 
// пользователь должен увидеть стектрейс ошибки.

// Данная промежуточная аттестация оценивается по системе "зачет" / "не зачет"

// "Зачет" ставится, если слушатель успешно выполнил задание.

// "Незачет" ставится, если слушатель не выполнил задание.

// Критерии оценивания: Слушатель написал приложение, которое запрашивает у пользователя следующие данные, 
// разделенные пробелом: Фамилия Имя Отчество дата _ рождения номер _ телефона пол

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DZTask1 {
    public static void main(String[] args) {
        try {
            info();
            System.out.println("success");
        } catch (FileSystemException e){
            System.out.println(e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void info() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "Введите информацию о себе в формате:" +
                        "Иванов Иван Иванович 01.01.2000 89139131313 m");
        String text = scanner.nextLine();
        String[] info = text.split(" ");
        if (info.length != 6) {
            throw new Exception("Введено неверное количество параметров");
        }
        String surname = info[0];
        String name = info[1];
        String patronymic = info[2];
    
        SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        Date birthdate;
        try {
            birthdate = format.parse(info[3]);
        } catch (ParseException e) {
            throw new ParseException("Неверный формат даты рождения", e.getErrorOffset());
        }
        int phone;
        try {
            phone = Integer.parseInt(info[4]);
        } catch (NumberFormatException e){
            throw new NumberFormatException("Неверный формат телефона");
        }
        String sex = info[5];
        if (!sex.toLowerCase().equals("m") && !sex.toLowerCase().equals("f")){
            throw new RuntimeException("Неверно введен пол");
        }
        String fileName = surname.toLowerCase() + ".txt";
        File file = new File(fileName);
        try (FileWriter fileWriter = new FileWriter(file, true)){
            if (file.length() > 0){
                fileWriter.write('\n');
            }
            fileWriter.write(String.format("%s %s %s %s %s %s", surname, name, patronymic, format.format(birthdate), phone, sex));
        } catch (IOException e){
            throw new FileSystemException("Возникла ошибка при работе с файлом");
        }
    }
}


