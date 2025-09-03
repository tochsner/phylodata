package com.phylodata.loader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

/**
 * Utility class for downloading experiment files from phylodata.com
 */
public class FileDownloader {

    private static final HttpClient httpClient = HttpClient.newBuilder().build();

    /**
     * Downloads an experiment file and stores it in the given directory.
     * If no version is given, the most recent one is downloaded.
     *
     * @param directory the directory to store the downloaded file
     * @param experiment the experiment identifier
     * @param fileName the name of the file to download
     * @param version the specific version to download (null for latest)
     * @param forceDownload whether to download even if file already exists
     * @return the path to the downloaded file
     * @throws IllegalArgumentException if the file could not be found
     * @throws IOException if an I/O error occurs during download
     */
    public static Path downloadFile(
            Path directory,
            String experiment,
            String fileName,
            Integer version,
            boolean forceDownload
    ) throws IOException {
        Path downloadedFile = directory.resolve(fileName);

        if (!forceDownload && Files.exists(downloadedFile)) {
            return downloadedFile;
        }

        Files.createDirectories(directory);

        try {
            String downloadLink = getDownloadLink(experiment, fileName, version);

            HttpRequest downloadRequest = HttpRequest.newBuilder()
                    .uri(URI.create(downloadLink))
                    .header("Content-Type", "multipart/form-data")
                    .timeout(Duration.ofMinutes(60))
                    .build();

            HttpResponse<byte[]> response = httpClient.send(
                    downloadRequest,
                    HttpResponse.BodyHandlers.ofByteArray()
            );

            if (response.statusCode() == 404) {
                throw new IllegalArgumentException("Unknown experiment or file.");
            }

            if (response.statusCode() != 200) {
                throw new IOException("Failed to download file. HTTP status: " + response.statusCode());
            }

            Files.write(downloadedFile, response.body());
            return downloadedFile;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Download was interrupted", e);
        }
    }

    /**
     * Gets the download link for a file from the phylodata API.
     */
    private static String getDownloadLink(String experiment, String fileName, Integer version)
            throws IOException, InterruptedException {

        String apiUrl;
        if (version != null) {
            apiUrl = String.format("https://phylodata.com/api/getDownloadLink/%s/%s/%d",
                    experiment, fileName, version);
        } else {
            apiUrl = String.format("https://phylodata.com/api/getDownloadLink/%s/%s",
                    experiment, fileName);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .timeout(Duration.ofSeconds(30))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to get download link. HTTP status: " + response.statusCode());
        }

        return response.body().trim();
    }
}