package ru.af;

import ru.af.dao.FileCsvReader;
import ru.af.dao.FileCsvWriter;
import ru.af.entity.OutLine;
import ru.af.entity.Session;

import java.io.File;
import java.io.Writer;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class FIleTask implements Runnable {
    private Path path;

    public FIleTask(Path path) {
        this.path = path;
    }

    @Override
    public void run() {
        FileCsvReader reader = new FileCsvReader();
        Processor p = new Processor();
        List<Session> los= p.separateSessions(reader.read(path.toAbsolutePath().toString()));

        Map<Long, List<Session>> map =p.groupByDate(los);
        Map<Long, List<OutLine>> result = p.formStatistics(map);
        FileCsvWriter w = new FileCsvWriter();
        String outFileName = path.getFileName().toString();
        w.write(result,outFileName);
    }
}
