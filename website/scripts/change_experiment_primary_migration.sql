-- Step 1: Drop foreign key constraints that reference experiments.id
ALTER TABLE "evolutionaryModels" DROP CONSTRAINT IF EXISTS "evolutionaryModels_experimentId_fkey";
ALTER TABLE "files" DROP CONSTRAINT IF EXISTS "files_experimentId_fkey";
ALTER TABLE "trees" DROP CONSTRAINT IF EXISTS "trees_experimentId_fkey";
ALTER TABLE "metadata" DROP CONSTRAINT IF EXISTS "metadata_experimentId_fkey";
ALTER TABLE "samples" DROP CONSTRAINT IF EXISTS "samples_experimentId_fkey";

-- Step 2: Drop the current primary key on experiments.id
ALTER TABLE "experiments" DROP CONSTRAINT IF EXISTS "experiments_pkey";

-- Step 3: Make humanReadableId NOT NULL if it isn't already
ALTER TABLE experiments ALTER COLUMN "humanReadableId" SET NOT NULL;

-- Step 4: Add new primary key constraint on humanReadableId
ALTER TABLE experiments ADD CONSTRAINT experiments_pkey PRIMARY KEY ("humanReadableId");

-- Step 5: Add columns for the new foreign key references in related tables
-- (assuming you need to add humanReadableId columns to reference the new PK)
ALTER TABLE "evolutionaryModels" ADD COLUMN "humanReadableId" varchar;
ALTER TABLE "trees" ADD COLUMN "humanReadableId" varchar;
ALTER TABLE "files" ADD COLUMN "humanReadableId" varchar;
ALTER TABLE "metadata" ADD COLUMN "humanReadableId" varchar;
ALTER TABLE "samples" ADD COLUMN "humanReadableId" varchar;

-- Step 6: Update the new foreign key columns with values from experiments table
UPDATE "evolutionaryModels" em 
SET "humanReadableId" = e."humanReadableId"
FROM "experiments" e 
WHERE em."experimentId" = e.id;

UPDATE "files" f 
SET "humanReadableId" = e."humanReadableId"
FROM "experiments" e 
WHERE f."experimentId" = e.id;

UPDATE "metadata" m 
SET "humanReadableId" = e."humanReadableId"
FROM "experiments" e 
WHERE m."experimentId" = e.id;

UPDATE "samples" s 
SET "humanReadableId" = e."humanReadableId"
FROM "experiments" e 
WHERE s."experimentId" = e.id;

UPDATE "trees" t 
SET "humanReadableId" = e."humanReadableId"
FROM "experiments" e 
WHERE t."experimentId" = e.id;

-- Step 7: Make the new foreign key columns NOT NULL
ALTER TABLE "evolutionaryModels" ALTER COLUMN "humanReadableId" SET NOT NULL;
ALTER TABLE "files" ALTER COLUMN "humanReadableId" SET NOT NULL;
ALTER TABLE "metadata" ALTER COLUMN "humanReadableId" SET NOT NULL;
ALTER TABLE "samples" ALTER COLUMN "humanReadableId" SET NOT NULL;
ALTER TABLE "trees" ALTER COLUMN "humanReadableId" SET NOT NULL;

-- Step 8: Add foreign key constraints referencing the new primary key with CASCADE DELETE
ALTER TABLE "evolutionaryModels" 
ADD CONSTRAINT fk_evolutionaryModels_humanReadableId 
FOREIGN KEY ("humanReadableId") REFERENCES "experiments"("humanReadableId") ON DELETE CASCADE;

ALTER TABLE "files" 
ADD CONSTRAINT fk_files_humanReadableId 
FOREIGN KEY ("humanReadableId") REFERENCES "experiments"("humanReadableId") ON DELETE CASCADE;

ALTER TABLE "metadata" 
ADD CONSTRAINT fk_metadata_humanReadableId 
FOREIGN KEY ("humanReadableId") REFERENCES "experiments"("humanReadableId") ON DELETE CASCADE;

ALTER TABLE "samples" 
ADD CONSTRAINT fk_samples_humanReadableId 
FOREIGN KEY ("humanReadableId") REFERENCES "experiments"("humanReadableId") ON DELETE CASCADE;

ALTER TABLE "trees" 
ADD CONSTRAINT fk_trees_humanReadableId 
FOREIGN KEY ("humanReadableId") REFERENCES "experiments"("humanReadableId") ON DELETE CASCADE;

-- Step 9: Drop the old experimental columns (optional - you may want to keep them for a transition period)
ALTER TABLE "evolutionaryModels" DROP COLUMN "experimentId";
ALTER TABLE "files" DROP COLUMN "experimentId";
ALTER TABLE "metadata" DROP COLUMN "experimentId";
ALTER TABLE "samples" DROP COLUMN "experimentId";
ALTER TABLE "trees" DROP COLUMN "experimentId";
ALTER TABLE "experiments" DROP COLUMN "id";
