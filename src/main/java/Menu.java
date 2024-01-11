
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;


public class Menu {
    //Хранение меню (Название - цена)
    private final HashMap<String, Double> menu = new HashMap<>();

    //Запрос цены по блюду
    public Double getCost(String item) {
        return menu.get(item);
    }

    //Загрузка меню из EXCEL (наименования блюд + цены) файла в HashMap
    public Menu() throws FileNotFoundException {
        String s;
        double d;
        FileInputStream file = new FileInputStream("menu.xls");
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert workbook != null;
        HSSFSheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                s = cellIterator.next().getStringCellValue();
                d = cellIterator.next().getNumericCellValue();
                menu.put(s, d);
            }
        }
    }

    //Возвращает массив наименований блюд из меню
    public String[] getMenu() {
        String[] l = new String[this.menu.size()];
        int i = 0;
        for (String key : this.menu.keySet()) {
            l[i++] = key;
        }
        return l;
    }
}
