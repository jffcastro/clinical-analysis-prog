package app.domain.shared;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GenericClass<E> implements Serializable {

    private static final long serialVersionUID = -9215818527600346760L;

    E element;

    /**
     * Constructor of the Generics class.
     */
    public GenericClass() {
    }

    private List<E> importedList = new ArrayList<>();

    /**
     * Writes the registered information to a binary file.
     *
     * @param fileName: path of the file that is going to receive the information.
     * @param list:     list containing the information to be exported to the file.
     */

    public void binaryFileWrite(String fileName, List<E> list) {
        File file = new File(fileName);
        List<E> listToWrite = new ArrayList<>(importedList);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            try {
                for (int listPosition = importedList.size(); listPosition < list.size(); listPosition++) {
                    if (!importedList.contains(list.get(listPosition))){
                        listToWrite.add(list.get(listPosition));
                    }

                }
                out.writeObject(listToWrite);
            } finally {
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Reads information from a binary file.
     *
     * @param fileName:       path of the file containing the information.
     * @param listToBeFilled: list that is going to be filled with the file's information.
     */
    public void binaryFileRead(String fileName, List<E> listToBeFilled) throws EOFException {
        File file = new File(fileName);
        if (file.exists()) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                importedList = (List<E>) in.readObject();
                in.close();
                listToBeFilled.addAll(importedList);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}