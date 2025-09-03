package com.phylodata.loader;

import com.phylodata.types.*;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilesPreferenceTest {

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
    public void testOnlyFullFilesWhenPreferFalse() {
        List<File> files = new ArrayList<>();
        File fullXml = new File();
        fullXml.setName("beast.xml");
        fullXml.setType(File.FileType.BEAST_2_CONFIGURATION);
        fullXml.setLocalPath(Path.of("beast.xml"));
        files.add(fullXml);

        File previewXml = new File();
        previewXml.setName("beast2 (preview).xml");
        previewXml.setType(File.FileType.BEAST_2_CONFIGURATION);
        previewXml.setLocalPath(Path.of("beast2 (preview).xml"));
        previewXml.setIsPreview(true);
        files.add(previewXml);

        File fullLogs = new File();
        fullLogs.setName("beast2.logs");
        fullLogs.setType(File.FileType.BEAST_2_POSTERIOR_LOGS);
        fullLogs.setLocalPath(Path.of("beast2.logs"));
        files.add(fullLogs);

        File previewLogs = new File();
        previewLogs.setName("beast2 (preview).logs");
        previewLogs.setType(File.FileType.BEAST_2_POSTERIOR_LOGS);
        previewLogs.setLocalPath(Path.of("beast2 (preview).logs"));
        previewLogs.setIsPreview(true);
        files.add(previewLogs);

        PaperWithExperiment p = buildExperiment(files);
        List<File> got = Files.getFiles(p, false);
        assertEquals(2, got.size());
        assertEquals("beast.xml", got.get(1).getName());
        assertEquals("beast2.logs", got.get(0).getName());
    }

    @Test
    public void testPreferPreviewWhenTrue() {
        List<File> files = new ArrayList<>();
        File fullXml = new File();
        fullXml.setName("beast2.xml");
        fullXml.setType(File.FileType.BEAST_2_CONFIGURATION);
        fullXml.setLocalPath(Path.of("beast2.xml"));
        files.add(fullXml);

        File previewXml = new File();
        previewXml.setName("beast2 (preview).xml");
        previewXml.setType(File.FileType.BEAST_2_CONFIGURATION);
        previewXml.setLocalPath(Path.of("beast2 (preview).xml"));
        previewXml.setIsPreview(true);
        files.add(previewXml);

        File previewLogs = new File();
        previewLogs.setName("beast2 (preview).logs");
        previewLogs.setType(File.FileType.BEAST_2_POSTERIOR_LOGS);
        previewLogs.setLocalPath(Path.of("beast2 (preview).logs"));
        previewLogs.setIsPreview(true);
        files.add(previewLogs);

        File previewTrees = new File();
        previewTrees.setName("beast2 (preview).trees");
        previewTrees.setType(File.FileType.POSTERIOR_TREES);
        previewTrees.setLocalPath(Path.of("beast2 (preview).trees"));
        previewTrees.setIsPreview(true);
        files.add(previewTrees);

        PaperWithExperiment p = buildExperiment(files);
        List<File> got = Files.getFiles(p, true);
        assertEquals(3, got.size());
        assertEquals("beast2 (preview).xml", got.get(1).getName());
        assertEquals("beast2 (preview).logs", got.get(0).getName());
        assertEquals("beast2 (preview).trees", got.get(2).getName());
    }
}


