CREATE INDEX ON public.files USING btree ("experimentId");
CREATE INDEX ON public.samples USING btree ("experimentId");
CREATE INDEX ON public."sampleData" USING btree ("sampleId");
CREATE INDEX ON public.classifications USING btree ("sampleId");
CREATE INDEX ON public.trees USING btree ("experimentId");
CREATE INDEX ON public."evolutionaryModels" USING btree ("experimentId");