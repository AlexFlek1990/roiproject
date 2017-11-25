package ru.af;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

public class Main {

    static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        final String inputPath = PropertyHolder.getInstance().INPUT_FOLDER;

        List<Path> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream =
                     Files.newDirectoryStream(Paths.
                             get(inputPath))) {
            for (Path path : directoryStream) {
                fileNames.add(path);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(" WELCOME ");
        System.out.println();

        //обработать файлы
        doWork(fileNames);

        //следить за новыми файлами
        watchFroNewFiles(inputPath);
    }

    private static void watchFroNewFiles(String input) {

        Path folder = FileSystems.getDefault().getPath(input + File.separator);

        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();

            // ENTRY_DELETE, ENTRY_MODIFY
            folder.register(watcher, ENTRY_CREATE);

            while (true) {

                // wait for key to be signaled
                WatchKey key;
                try {
                    key = watcher.take();
                } catch (InterruptedException x) {
                    return;
                }
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    if (kind == ENTRY_CREATE) {
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path filename = ev.context();
                        Path fixFile  = Paths.get(PropertyHolder.getInstance().INPUT_FOLDER + File.separator + filename);
                        doWork(fixFile);

                    }
                }

                boolean valid = key.reset();
                if (!valid) {
                    System.out.println("Directory is not valid");
                    break;
                }
                Thread.sleep(2000);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private static void doWork(List<Path> fileNames) {
        for (Path fileName : fileNames) {
            if (fileName != null && fileName.toString().contains(".csv")) {
                executorService.submit(new FileTask(fileName));
            }
        }
    }

    private static void doWork(Path path) {
        if (path != null && path.toString().contains(".csv")) {
            executorService.submit(new FileTask(path));
        }
    }
}