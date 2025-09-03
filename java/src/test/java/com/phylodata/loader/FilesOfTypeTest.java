package com.phylodata.loader;

import com.phylodata.types.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilesOfTypeTest {

    private PaperWithExperiment buildExperiment(List<File> files) {
        PaperWithExperiment p = new PaperWithExperiment();
        p.setFiles(files);

        Paper paper = new Paper();
        paper.setDoi("");
        paper.setTitle("");
        paper.setYear(2018);
        p.setPaper(paper);

        EditableExperiment editable = new EditableExperiment();
        NonEditableExperiment nonEditable = new NonEditableExperiment();
        nonEditable.setHumanReadableId("");
        nonEditable.setOrigin("");
        nonEditable.setUploadDate(LocalDate.now().toString());
        nonEditable.setVersion(0);
        Experiment exp = Experiment.fromPartial(editable, nonEditable);
        exp.setTitle("");
        exp.setDescription("");
        p.setExperiment(exp);

        Metadata m = new Metadata();
        m.setEvoDataPipelineVersion("");
        p.setMetadata(m);
        p.setSamples(new ArrayList<>());
        p.setEvolutionaryModel(new ArrayList<>());
        p.setTrees(null);
        return p;
    }

    @Test
    public void testSinglePossibleFileGetFound() throws FileNotFoundException {
        List<File> files = new ArrayList<>();
        File config = new File();
        config.setName("beast.xml");
        config.setType(File.FileType.BEAST_2_CONFIGURATION);
        config.setLocalPath(Path.of("beast.xml"));
        files.add(config);

        File posterior = new File();
        posterior.setName("beast.trees");
        posterior.setType(File.FileType.POSTERIOR_TREES);
        posterior.setLocalPath(Path.of("beast.trees"));
        files.add(posterior);

        File summary = new File();
        summary.setName("summary.trees");
        summary.setType(File.FileType.SUMMARY_TREE);
        summary.setLocalPath(Path.of("summary.trees"));
        files.add(summary);

        PaperWithExperiment p = buildExperiment(files);

        File gotConfig = p.getFileOfType(File.FileType.BEAST_2_CONFIGURATION);
        assertNotNull(gotConfig);
        assertEquals("beast.xml", gotConfig.getName());

        File gotPosterior = p.getFileOfType(File.FileType.POSTERIOR_TREES);
        assertNotNull(gotPosterior);
        assertEquals("beast.trees", gotPosterior.getName());

        File gotSummary = p.getFileOfType(File.FileType.SUMMARY_TREE);
        assertNotNull(gotSummary);
        assertEquals("summary.trees", gotSummary.getName());
    }

    @Test
    public void testMissingFileTypeThrows() throws FileNotFoundException {
        List<File> files = new ArrayList<>();
        File config = new File();
        config.setName("beast.xml");
        config.setType(File.FileType.BEAST_2_CONFIGURATION);
        config.setLocalPath(Path.of("beast.xml"));
        files.add(config);

        PaperWithExperiment p = buildExperiment(files);
        assertThrows(FileNotFoundException.class, () -> p.getFileOfType(File.FileType.SUMMARY_TREE));
    }

    @Test
    public void testMultiplePossibleFilesReturnsFirst() throws FileNotFoundException {
        List<File> files = new ArrayList<>();
        File one = new File();
        one.setName("beast.xml");
        one.setType(File.FileType.BEAST_2_CONFIGURATION);
        one.setLocalPath(Path.of("beast.xml"));
        files.add(one);

        File two = new File();
        two.setName("beast2.xml");
        two.setType(File.FileType.BEAST_2_CONFIGURATION);
        two.setLocalPath(Path.of("beast2.xml"));
        files.add(two);

        PaperWithExperiment p = buildExperiment(files);
        File got = p.getFileOfType(File.FileType.BEAST_2_CONFIGURATION);
        assertNotNull(got);
        assertEquals("beast.xml", got.getName());
    }
}


