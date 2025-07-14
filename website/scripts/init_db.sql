-- Papers table (using doi as primary key)
CREATE TABLE "papers" (
    "doi" VARCHAR(255) PRIMARY KEY,
    "title" TEXT NOT NULL,
    "year" INTEGER NOT NULL,
    "authors" TEXT[], -- PostgreSQL array type for string array
    "abstract" TEXT NOT NULL,
    "bibtex" TEXT NOT NULL,
    "url" TEXT
);

-- Experiments table
CREATE TABLE "experiments" (
    "id" VARCHAR(255) PRIMARY KEY,
    "type" VARCHAR(50) NOT NULL DEFAULT 'beast2Experiment',
    "title" TEXT,
    "description" TEXT,
    "humanReadableId" VARCHAR(255) NOT NULL,
    "origin" TEXT NOT NULL,
    "uploadDate" TIMESTAMP NOT NULL,
    "license" TEXT,
    "paperDoi" VARCHAR(255),
    FOREIGN KEY ("paperDoi") REFERENCES "papers"("doi")
);

-- Files table
CREATE TABLE "files" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    "type" VARCHAR(50) NOT NULL CHECK ("type" IN (
        'beast2Configuration',
        'beast2PosteriorLogs',
        'beast2PosteriorTrees',
        'summaryTree',
        'codephyModel',
        'phyloDataExperiment',
        'unknown'
    )),
    "version" INTEGER NOT NULL,
    "sizeBytes" BIGINT NOT NULL,
    "md5" VARCHAR(32) NOT NULL,
    "isPreview" BOOLEAN DEFAULT FALSE,
    "localPath" TEXT,
    "remotePath" TEXT,
    "experimentId" VARCHAR(255),
    FOREIGN KEY ("experimentId") REFERENCES "experiments"("id")
);

-- Sample data table
CREATE TABLE "sampleData" (
    "id" SERIAL PRIMARY KEY,
    "type" VARCHAR(50) NOT NULL CHECK ("type" IN (
        'rna',
        'dna',
        'aminoAcids',
        'phasedDiploidDna',
        'traits',
        'unknown'
    )),
    "length" INTEGER NOT NULL,
    "data" TEXT NOT NULL,
    "sampleId" VARCHAR(255)
);

-- Classification entries table
CREATE TABLE "classificationEntries" (
    "id" VARCHAR(255) PRIMARY KEY,
    "classificationId" VARCHAR(255) NOT NULL,
    "scientificName" VARCHAR(255) NOT NULL,
    "idType" VARCHAR(50) NOT NULL CHECK ("idType" IN ('ncibTaxonomyId', 'glottologId')),
    "commonName" TEXT,
    "sampleId" VARCHAR(255)
);

-- Samples table
CREATE TABLE "samples" (
    "id" VARCHAR(255) PRIMARY KEY,
    "sampleId" VARCHAR(255) NOT NULL,
    "scientificName" VARCHAR(255) NOT NULL,
    "type" VARCHAR(50) NOT NULL CHECK ("type" IN ('species', 'cells', 'language', 'unknown')),
    "commonName" TEXT,
    "experimentId" VARCHAR(255),
    FOREIGN KEY ("experimentId") REFERENCES "experiments"("id")
);

-- Trees table
CREATE TABLE "trees" (
    "id" SERIAL PRIMARY KEY,
    "numberOfTrees" INTEGER NOT NULL,
    "numberOfTips" INTEGER NOT NULL,
    "ultrametric" BOOLEAN NOT NULL,
    "timeTree" BOOLEAN NOT NULL,
    "rooted" BOOLEAN NOT NULL,
    "ccd1Entropy" DECIMAL NOT NULL,
    "treeEss" DECIMAL NOT NULL,
    "ccd0MapTree" TEXT NOT NULL,
    "hipstrTree" TEXT NOT NULL,
    "leafToSampleMap" JSONB NOT NULL, -- JSON for Record<string, string>
    "averageRootAge" DECIMAL NOT NULL,
    "experimentId" VARCHAR(255),
    FOREIGN KEY ("experimentId") REFERENCES "experiments"("id")
);

-- Evolutionary model components table
CREATE TABLE "evolutionaryModelComponents" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    "description" TEXT NOT NULL,
    "type" VARCHAR(50) NOT NULL CHECK ("type" IN (
        'substitutionModel',
        'clockModel',
        'treePrior',
        'treeLikelihood',
        'other'
    )),
    "documentationUrl" TEXT NOT NULL,
    "parameters" JSONB NOT NULL, -- JSON for Record<string, any>
    "evolutionaryModelId" INTEGER
);

-- Evolutionary models table
CREATE TABLE "evolutionaryModels" (
    "id" SERIAL PRIMARY KEY,
    "experimentId" VARCHAR(255),
    FOREIGN KEY ("experimentId") REFERENCES "experiments"("id")
);

-- Metadata table
CREATE TABLE "metadata" (
    "id" SERIAL PRIMARY KEY,
    "evoDataPipelineVersion" VARCHAR(255) NOT NULL,
    "experimentId" VARCHAR(255),
    FOREIGN KEY ("experimentId") REFERENCES "experiments"("id")
);

-- Add foreign key constraints for relationships
ALTER TABLE "sampleData"
ADD FOREIGN KEY ("sampleId") REFERENCES "samples"("id");

ALTER TABLE "classificationEntries"
ADD FOREIGN KEY ("sampleId") REFERENCES "samples"("id");

ALTER TABLE "evolutionaryModelComponents"
ADD FOREIGN KEY ("evolutionaryModelId") REFERENCES "evolutionaryModels"("id");

-- Create indexes for better performance
CREATE INDEX "idx_experiments_paperDoi" ON "experiments"("paperDoi");
CREATE INDEX "idx_files_experimentId" ON "files"("experimentId");
CREATE INDEX "idx_samples_experimentId" ON "samples"("experimentId");
CREATE INDEX "idx_sampleData_sampleId" ON "sampleData"("sampleId");
CREATE INDEX "idx_classificationEntries_sampleId" ON "classificationEntries"("sampleId");
CREATE INDEX "idx_trees_experimentId" ON "trees"("experimentId");
CREATE INDEX "idx_evolutionaryModels_experimentId" ON "evolutionaryModels"("experimentId");
CREATE INDEX "idx_metadata_experimentId" ON "metadata"("experimentId");
