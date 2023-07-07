import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in); // создаем сканер
        String str = scan.nextLine(); // считываем строку в переменную str

        try {
            System.out.println(Calc(str)); // пытаемся применить метод Calc к введенной строке
        } catch (IllegalArgumentException e) { // выбрасываем исключение
            System.out.println("Ошибка - " + e.getMessage());
        }
    }

    static String Calc(String input) { // создание метода, который принимает 2 числа и знак и проводит вычисления
        String[] arr = input.split(" "); // считанную строку разбиваем пробелами и каждое значение записываем в массив arr, тип String
        String op = arr[1]; // записываем в переменную второе значение массива, тип String

        int result; // создание пустой переменной, в которую будет записан результат в зависимости от переданного знака
        int num1; // создание переменной для первого введенного числа
        int num2; // создание переменной для второго введенного числа
        boolean isRoman = isRoman(arr[0]) && isRoman(arr[2]); // булевая переменная которая проверяет являются ли оба числа римскими

        if (arr.length != 3) {
            throw new IllegalArgumentException("Введено неверное выражение.");
        } // проверка на кол-во введенных значений

        if (isRoman) { // условие, если числа римские, то
            num1 = RomanToArabic(arr[0]); // переводим 1 число в арабское
            num2 = RomanToArabic(arr[2]); // переводим 2 число в арабское
        } else { // если числа не римские, то
            num1 = Integer.parseInt(arr[0]); // переводим строковое значение числа в числовое
            num2 = Integer.parseInt(arr[2]); // переводим строковое значение числа в числовое
        }

        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new IllegalArgumentException("По заданию числа должны быть от 1 до 10");
        } // проверка на размерность чисел num1 и num2

        switch (op) { // перебираем арифметические знаки
            case "+" -> result = num1 + num2; // если +, то суммируем num1 и num2 и записываем в result
            case "-" -> result = num1 - num2;
            case "*" -> result = num1 * num2;
            case "/" -> result = num1 / num2;
            default -> throw new IllegalArgumentException("Введен неверный оператор - " + op); // если знак не 1 из 4, выбрасываем исключение
        }
        if(isRoman) { // если число римское, то
            if(result < 1) { // если римское число меньше 1, то выбрасываем исключение
                throw new IllegalArgumentException("Результат римских цифр не может быть меньше 1");
            }
            return ArabicToRoman(result); // после вычисления, переводим арабское число обратно в римское
        } else { // если не римское, то выводим результат, тип String
            return String.valueOf(result);
        }
    }

    static boolean isRoman(String input) { // метод, проверяющий являетсся ли число римским
        String romanLetters = "IVXLCDM"; // строка, содержащая все возможные римские обозначения цифр
        for(char c : input.toCharArray()) { // проходим циклом по массиву символов строки
            if(romanLetters.contains(String.valueOf(c))) { // если римские обозначения присутствуют в символе
                return true; // возвращаем true
            }
        }
        return false; // если не присутствуют -> false
    }

    static int RomanToArabic(String roman){ // метод перевода из римских в арабские
        Map<Character, Integer> romanNumbers = new HashMap<>(); // создаем новый Map, типы Character и Integer
        {
            romanNumbers.put('I', 1); // кладем значение для ключа "I" = 1 и т.д.
            romanNumbers.put('V', 5);
            romanNumbers.put('X', 10);
            romanNumbers.put('L', 50);
            romanNumbers.put('C', 100);
            romanNumbers.put('D', 500);
            romanNumbers.put('M', 1000);
        }
        int res = 0; // создаем переменную, которую потом будем возвращать
        int previousCh = 0; // переменная предыдущего символа
        for(int i = roman.length() - 1; i >= 0; i--) { // проходим циклом от длины строки - 1 до 0
            int currentCh = romanNumbers.get(roman.charAt(i)); // в переменную currentCh кладем значение i-го символа
            if(currentCh < previousCh) { // проверка, если currentCh < previousCh
                res -= currentCh; // то из результирующей переменной вычитаем currentCh и записываем ее заново
            } else {
                res += currentCh; // если условие не выполняется, то суммируем currentCh и записываем res заново
            }
            previousCh = currentCh; // в конце итерации присваем значение previousCh к currentCh
        }
        return res; // возвращаем результат
    }
    static String ArabicToRoman(int number) { // метод конвертации арабских в римские
        if (number < 1) { // проверка на входящее число, должно быть > 0
            throw new IllegalArgumentException("Число должно быть положительным");
        }

        StringBuilder result = new StringBuilder(); // создаем новую строку

        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1}; // создаем массив с арабскими значениями
        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"}; // создаем массив с римскими значениями

        int i = 0;
        while (number > 0) { // начинаем цикл, пока переданное число > 0
            if (number >= arabicValues[i]) { // проверка, переданное число больше числа по счетчику в массиве?
                result.append(romanSymbols[i]); // если больше, в строку result добавляем римский символ
                number -= arabicValues[i]; // переданное число уменьшаем на число из массива
            } else {
                i++; // увеличиваем счетчик
            }
        }
        return result.toString(); // возвращаем результат
    }
}