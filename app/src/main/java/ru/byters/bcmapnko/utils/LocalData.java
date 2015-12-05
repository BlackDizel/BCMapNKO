package ru.byters.bcmapnko.utils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import ru.byters.bcmapnko.model.Task;

public class LocalData {
    private final static String fileData = "data_tasks";

    public static void saveData(Context context, List<Task> items) {

        ObjectOutputStream objectOut = null;
        try {
            FileOutputStream fileOut = context.openFileOutput(fileData, Context.MODE_PRIVATE);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(items);
            fileOut.getFD().sync();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOut != null) {
                try {
                    objectOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void addData(Context context, Task item) {
        List<Task> items = readData(context);
        if (items == null) items = new ArrayList<>();
        items.add(item);
        saveData(context, items);
    }

    private static boolean isFileExist(Context context, String filename) {
        String[] files = context.fileList();
        if (files == null || files.length == 0) return false;
        for (String fileName : files)
            if (fileName.equals(filename))
                return true;
        return false;
    }

    public static List<Task> readData(Context context) {
        if (!isFileExist(context, fileData))
            return null;

        ObjectInputStream objectIn = null;
        try {

            FileInputStream fileIn = context.getApplicationContext().openFileInput(fileData);
            objectIn = new ObjectInputStream(fileIn);
            return (List<Task>) objectIn.readObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            //todo remove file
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //todo remove file
        } finally {
            if (objectIn != null) {
                try {
                    objectIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
