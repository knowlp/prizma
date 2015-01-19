package com.hrzafer.prizma.data.io;

import com.hrzafer.prizma.data.*;

import java.io.File;
import java.io.FileFilter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Reads a dataset from a directory
 */
public class DirectoryDatasetReader extends DatasetReader {
    private File datasetDirectory;
    private FileDocumentSourceStrategy strategy;
    private boolean createId;

    public static class Builder{

        private double percentage = DatasetReader.DEFAULT_PERCENTAGE;
        private boolean shuffled = DatasetReader.DEFAULT_SHUFFLED_OPTION;
        private Charset charset = DatasetReader.DEFAULT_CHARSET;
        private  boolean createId = true;
        private FileDocumentSourceStrategy strategy = new DefaultFileDocumentSourceStrategy();
        private final String datasetDirectoryPath;

        public Builder(String datasetDirectoryPath){
            this.datasetDirectoryPath = datasetDirectoryPath;
        }

        public Builder percentage(int percentage){
            this.percentage = percentage;
            return this;
        }

        public Builder shuffled(boolean shuffled){
            this.shuffled = shuffled;
            return this;
        }

        public Builder charset(Charset charset){
            this.charset=charset;
            return this;
        }

        public Builder createId(boolean createId){
            this.createId = createId;
            return this;
        }

        public  Builder strategy(FileDocumentSourceStrategy strategy){
            this.strategy = strategy;
            return this;
        }

        public DirectoryDatasetReader build(){
            return new DirectoryDatasetReader(this);
        }

    }

    private DirectoryDatasetReader(Builder builder){
        datasetDirectory = new File(builder.datasetDirectoryPath);
        this.createId = builder.createId;
        this.percentage = builder.percentage;
        this.shuffled = builder.shuffled;
        this.charset = builder.charset;
        this.strategy = builder.strategy;

    }

    @Override
    public Dataset read() {
        List<DocumentCategory> categories = readCategories();
        return new Dataset(categories);
    }



    private List<DocumentCategory> readCategories() {
        File[] categoryDirs = getSubdirectoriesIgnoreFiles(datasetDirectory);
        List<DocumentCategory> categories = new ArrayList<>();
        for (File categoryDir : categoryDirs) {
            DocumentCategory category = readCategory(categoryDir);
            categories.add(category);
        }
        return categories;
    }

    private DocumentCategory readCategory(File classDirectory) {
        if (hasSubdirectory(classDirectory)) {
            return readCategoryFromSubcategories(classDirectory);
        }

        return readCategoryFromInstances(classDirectory);
    }

    private DocumentCategoryFromSubcategories readCategoryFromSubcategories(File categoryDir) {
        List<DocumentCategoryFromDocuments> subcategories = new ArrayList<>();
        File[] subsetDirectories = getSubdirectoriesIgnoreFiles(categoryDir);
        for (File subsetDirectory : subsetDirectories) {
            DocumentCategoryFromDocuments subset = readCategoryFromInstances(subsetDirectory, categoryDir.getName());
            //CategoryFromInstances subset = readCategoryFromInstances(subsetDirectory);
            subcategories.add(subset);
        }
        return new DocumentCategoryFromSubcategories(subcategories, categoryDir.getName());
    }

    public DocumentCategoryFromDocuments readCategoryFromInstances(File categoryDir) {
        List<Document> documents = readAllInstances(categoryDir);
        return new DocumentCategoryFromDocuments(documents, categoryDir.getName());
    }

    public DocumentCategoryFromDocuments readCategoryFromInstances(File categoryDir, String parentDirectoryName) {
        List<Document> documents = readAllInstances(categoryDir, parentDirectoryName);
        return new DocumentCategoryFromDocuments(documents, parentDirectoryName);
    }

    private List<Document> readAllInstances(File directory) {
       return readAllInstances(directory, directory.getName());
    }

    private List<Document> readAllInstances(File directory, String parentDirectoryName) {
        List<Document> samples = new ArrayList<>();
        List<File> textFiles = selectTextFiles(getTextFilesIgnoreOthers(directory));
        for (File textFile : textFiles) {
            Document sample = new Document(new FileDocumentSource(textFile.getAbsolutePath(), strategy, createId), parentDirectoryName);
            samples.add(sample);
        }
        return samples;
    }

    private List<File> selectTextFiles(List<File> files){
        if (shuffled){
            Collections.shuffle(files);
        }
        int count = (int) Math.round((files.size() * percentage) / 100);
        return files.subList(0, count);
    }

    private boolean hasSubdirectory(File directory) {
        return getSubdirectoriesIgnoreFiles(directory).length > 0;
    }

    private File[] getSubdirectoriesIgnoreFiles(File directory) {
        return directory.listFiles(getDirectoryFilter());
    }

    private List<File> getTextFilesIgnoreOthers(File directory) {
        File[] files = directory.listFiles(getTextFileFilter());
        return Arrays.asList(files);
    }

    private FileFilter getDirectoryFilter() {
        return new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        };
    }

    private FileFilter getTextFileFilter() {
        return new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".txt");
            }
        };
    }


}
