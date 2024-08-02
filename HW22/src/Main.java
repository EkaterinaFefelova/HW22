import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String VALID_COMMAND_REPLACE_ADD_WITH_INDEX = "(изменить|добавить)\\s+\\d+\\s+.+";
    private static final String VALID_COMMAND_ADD_WITHOUT_INDEX = "добавить\\s+\\D+\\s*.*";
    private static final String VALID_COMMAND_REMOVE = "удалить\\s+\\d+";
    private static final String VALID_COMMAND_ONE_WORD = "(печать|выход|инфо)+";
    private static final int INVALID_COMMAND_LENGTH = 0;
    private static final int VALID_COMMAND_WITHOUT_INDEX_LENGTH = 2;
    private static final int VALID_COMMAND_WITH_INDEX_LENGTH = 3;
    private static final String INFO = "\nДоступные команды:\n" +
            "Добавить [дело]\n" +
            "Добавить [индекс] [дело]\n" +
            "Удалить [индекс]\n" +
            "Заменить [индекс] [новое дело]\n" +
            "Печать\n" +
            "Инфо\n";
    private static List <String> toDoList= new ArrayList<String>();

    public static void main(String[] args) {
        String[] input;
        do {
            input = getInput();
            while (input.length == INVALID_COMMAND_LENGTH) {
                System.out.println(INFO);
                input = getInput();
            }
            switch (input[0].toLowerCase()) {
                case "выход":
                    break;
                case "инфо":
                    System.out.println(INFO);
                    break;
                case "печать":
                    printAll(toDoList);
                    break;
                case "удалить":
                    remove(Integer.parseInt(input[1]));
                    break;
                case "изменить":
                    replace(Integer.parseInt(input[1]), input[2]);
                    break;
                case "добавить":
                    if (input.length == VALID_COMMAND_WITHOUT_INDEX_LENGTH)
                        add(input[1]);
                    else if (input.length == VALID_COMMAND_WITH_INDEX_LENGTH)
                        add(Integer.parseInt(input[1]), input[2]);
                    break;
            }
        }
        while ((!input[0].equalsIgnoreCase("выход")));
    }

    public static String[] getInput()
    {
        System.out.println("\nВведите команду: ");
        String input = new Scanner(System.in).nextLine().trim();
        if (input.toLowerCase().matches(VALID_COMMAND_REPLACE_ADD_WITH_INDEX)) {
            return input.split(" ", 3);
        }
        else if (input.toLowerCase().matches(VALID_COMMAND_ADD_WITHOUT_INDEX)||
                input.toLowerCase().matches(VALID_COMMAND_ONE_WORD)||
                input.toLowerCase().matches(VALID_COMMAND_REMOVE))
            return input.split(" ", 2);
        else{
            System.out.println("Введена недопустимая команда");
            return new String[0];
        }
    }

    public static void add(String task){
        if (toDoList.contains(task)){
            System.out.println("Дело " + task + " уже есть в списке");
            return;
        }
        toDoList.add(task);
        System.out.println("Добавлено дело: " + task);
    }

    public static void add(int index, String task){
        if (isNotValidIndex(index)) {
            toDoList.add(task);
            System.out.println("Нет места под номером " + index + ". Дело " + task + " добавлено в конец списка");
            return;
        }
        toDoList.add(index, task);
        System.out.println("На " + index + " место добавлено дело: " + task);
    }

    public static void remove(int index){
        if (isNotValidIndex(index)) {
            System.out.println("Дела под номером " + index + " нет в списке. Удаление невозможно");
            return;
        }
        String task = toDoList.get(index);
        toDoList.remove(index);
        System.out.println("Дело " + task + " успешно удалено");
    }

    public static void replace(int index, String task){
        if (isNotValidIndex(index)) {
            System.out.println("Дела под номером " + index + " нет в списке. Изменение невозможно");
            return;
        }
        String initialTask = toDoList.get(index);
        toDoList.set(index, task);
        System.out.println("Дело " + initialTask + " изменено на " + task);
    }

    public static void printAll(List<String> todolist){
        if (todolist.isEmpty()) {
            System.out.println("Список дел пуст!");
            return;
        }
        System.out.println("\n>>>Список дел<<<");
        for (String task : todolist)
            System.out.println("Дело: " + task);
    }

    public static boolean isNotValidIndex(int index) {
        return index < 0 || index >= toDoList.size();
    }
}