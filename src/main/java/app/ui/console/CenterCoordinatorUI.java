package app.ui.console;

import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CenterCoordinatorUI implements Runnable {

    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<>();

        options.add(new MenuItem("Import data from legacy systems.", new DataFromLegacySystemUI()));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\nCenter Coordinator Menu:");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        }
        while (option != -1);
    }
}
