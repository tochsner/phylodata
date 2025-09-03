package com.phylodata.loader;

import com.phylodata.types.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetFileTest {

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
    public void testSingleFileIsFound() throws FileNotFoundException {
        List<File> files = new ArrayList<>();
        File f = new File();
        f.setName("beast2.xml");
        f.setType(File.FileType.BEAST_2_CONFIGURATION);
        f.setLocalPath(Path.of("beast2.xml"));
        files.add(f);

        PaperWithExperiment p = buildExperiment(files);
        File got = p.getFile("beast2.xml");
        assertNotNull(got);
        assertEquals("beast2.xml", got.getName());
    }

    @Test
    public void testMissingFileThrows() throws FileNotFoundException {
        PaperWithExperiment p = buildExperiment(new ArrayList<>());
        assertThrows(FileNotFoundException.class, () -> p.getFile("beast2.xml"));
    }

    @Test
    public void testFullFileIsPreferredIfNothingSpecified() throws FileNotFoundException {
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

        PaperWithExperiment p = buildExperiment(files);
        File got = p.getFile("beast2.xml");
        assertNotNull(got);
        assertEquals("beast2.xml", got.getName());
    }

    @Test
    public void testFullFileIsPreferredIfPreviewIsFalse() throws FileNotFoundException {
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

        PaperWithExperiment p = buildExperiment(files);
        File got = p.getFile("beast2.xml", false);
        assertNotNull(got);
        assertEquals("beast2.xml", got.getName());
    }

    @Test
    public void testPreviewFileIsPreferredIfPreviewIsTrue() throws FileNotFoundException {
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

        PaperWithExperiment p = buildExperiment(files);
        File got = p.getFile("beast2.xml", true);
        assertNotNull(got);
        assertEquals("beast2 (preview).xml", got.getName());
    }
}


