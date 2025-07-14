CREATE TABLE "papers" (
    "doi" VARCHAR(255) PRIMARY KEY,
    "title" TEXT NOT NULL,
    "year" INTEGER NOT NULL,
    "authors" TEXT[], -- PostgreSQL array type for string array
    "abstract" TEXT NOT NULL,
    "bibtex" TEXT NOT NULL,
    "url" TEXT
);

CREATE TABLE "experiments" (
    "id" SERIAL PRIMARY KEY,
    "type" VARCHAR(50) NOT NULL DEFAULT 'beast2Experiment',
    "version" INTEGER NOT NULL,
    "title" TEXT,
    "description" TEXT,
    "humanReadableId" VARCHAR(255) NOT NULL,
    "origin" TEXT NOT NULL,
    "uploadDate" TIMESTAMP NOT NULL,
    "license" TEXT,
    "paperDoi" VARCHAR(255),
    FOREIGN KEY ("paperDoi") REFERENCES "papers"("doi")
);

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
    "sizeBytes" BIGINT NOT NULL,
    "md5" VARCHAR(32) NOT NULL,
    "isPreview" BOOLEAN DEFAULT FALSE,
    "experimentId" INTEGER,
    FOREIGN KEY ("experimentId") REFERENCES "experiments"("id")
);

CREATE TABLE "samples" (
    "id" SERIAL PRIMARY KEY,
    "sampleId" VARCHAR(255) NOT NULL,
    "scientificName" VARCHAR(255) NOT NULL,
    "type" VARCHAR(50) NOT NULL CHECK ("type" IN ('species', 'cells', 'language', 'unknown')),
    "commonName" TEXT,
    "experimentId" INTEGER,
    FOREIGN KEY ("experimentId") REFERENCES "experiments"("id")
);

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
    "sampleId" INTEGER,
    FOREIGN KEY ("sampleId") REFERENCES "samples"("id")
);

CREATE TABLE "classifications" (
    "id" SERIAL PRIMARY KEY,
    "classificationId" VARCHAR(255) NOT NULL,
    "scientificName" VARCHAR(255) NOT NULL,
    "idType" VARCHAR(50) NOT NULL CHECK ("idType" IN ('ncibTaxonomyId', 'glottologId')),
    "commonName" TEXT,
    "sampleId" INTEGER,
    FOREIGN KEY ("sampleId") REFERENCES "samples"("id")
);

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
    "experimentId" INTEGER,
    FOREIGN KEY ("experimentId") REFERENCES "experiments"("id")
);

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
    "experimentId" INTEGER,
    FOREIGN KEY ("experimentId") REFERENCES "experiments"("id")
);

CREATE TABLE "metadata" (
    "id" SERIAL PRIMARY KEY,
    "evoDataPipelineVersion" VARCHAR(255) NOT NULL,
    "experimentId" INTEGER,
    FOREIGN KEY ("experimentId") REFERENCES "experiments"("id")
);
