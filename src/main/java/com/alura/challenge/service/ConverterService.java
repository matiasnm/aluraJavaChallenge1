package com.alura.challenge.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.alura.challenge.dto.ratesRequestDto;
import com.alura.challenge.model.Converter;
import com.google.gson.Gson;

public class ConverterService {

    Converter converter;
    List<String> codes;
    Double quantity = 0.0;
    String from = "USD";
    String to = "";
    private Scanner scanner = new Scanner(System.in, "UTF-8");

    private static final String apiUrl = "https://v6.exchangerate-api.com/v6/";
    private static final String apiKey = "6ff65b8afcdc60c8d1d22737";

    private static final String RESET   = "\u001B[0m";
    private static final String RED     = "\u001B[31m";
    private static final String GREEN   = "\u001B[32m";
    private static final String BLUE    = "\u001B[34m";
    private static final String WHITE   = "\u001B[37m";

    public ConverterService() {
        setConverter();
    }

    public void init() {
        showBanner();
        String input = "";
        do {
            System.out.print("\n\n");
            showStats();
            showMenu();
            input = getUserInput("Ingrese una opción: ");
            switch (input) {
            case "1": 
                quantity = getQuantity();
                cls();
                System.out.println(RED + "Nueva cantidad ingresada: " + RESET + quantity);
                break;
            case "2":
                this.to = selectCurrency();
                Double result = convertTo(quantity, from, to);
                cls();
                System.out.println(RED + "Conversión de divisas: ");
                System.out.println(GREEN + quantity + WHITE + from + " -> " + RED + String.format("%.2f", result) + WHITE + to + RESET);
                break;
            case "3": 
                this.from = selectCurrency();
                cls();
                System.out.println(RED + "Nueva divisa seleccionada: " + RESET + from);
                break;
            case "4": 
                exit();
            default:
                cls();
                System.out.println(RED + "ERROR: Opción inválida." + RESET);
            }
        } while (!input.equals("4"));
    }

    public void exit() {
        scanner.close();
        System.out.println(RED + "Hasta la próxima!" + RESET);
        System.exit(0);
    }

    private void setConverter() {
        ratesRequestDto ratesRequestDto = getApiRequest();
        if (ratesRequestDto == null) {
            System.out.println("ERROR: No fue posible establecer la conección con la API.");
        } else {
            System.out.println("STATUS: " + ratesRequestDto.result());
            if (ratesRequestDto.result() != "succes") {
                System.out.println("ERROR: No fue posible conseguir la información de la API.");
            }
        }
        HashMap<String, Double> rates = ratesRequestDto.conversion_rates();
        this.converter = new Converter(rates);
        this.codes = new ArrayList<>(rates.keySet());
        Collections.sort(this.codes);
    }

    private ratesRequestDto getApiRequest() {
        Gson gson = new Gson();
        String currencyCode = "/latest/" + "USD";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl + apiKey + currencyCode))
            .build();
        try {
            HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            ratesRequestDto ratesRequestDto = gson.fromJson(body, ratesRequestDto.class);
            System.out.println("STATUS: Conección con la API establecida.");
            return ratesRequestDto;
        } catch (IOException | InterruptedException | IllegalArgumentException | SecurityException ex) {
            System.out.println("EXCEPCION: " + ex);
            return null;
        }
    }

    public String getUserInput(String msg) {
        System.out.print(BLUE + msg + RESET);
        return scanner.nextLine();
    }

    public Double parseDouble(String input, double min) {
        try {
            double parsedValue = Double.parseDouble(input);
            if (parsedValue < min) {
                System.out.println(RED + "ERROR: Ingrese un valor mayor a: " + min + RESET);
                return null;
            }
            return parsedValue;
        } catch (NumberFormatException ex) {
            System.out.println(RED + "ERROR: Valor ingresado inválido." + RESET);
            return null;
        }
    }

    public Double getQuantity() {
        Double doubleInput = null;
        do {
            String input = getUserInput("Ingrese una cantidad de dinero: ");
            doubleInput = parseDouble(input, 0);
        } while (doubleInput == null);
        return doubleInput;
    }

    public String selectCurrency() {
        String input = "";
        do {
            showCodes(5);
            input = getUserInput("Ingrese un código válido de la lista: ");
            input = input.toUpperCase();
            if (!codes.contains(input)) {
                System.out.println(RED + "ERROR: Código inválido." + RESET);
            }
        } while(!codes.contains(input));
        return input;
    }

    public Double convertTo(Double quantity, String from, String to) {
        return converter.convert(quantity, from, to);
    }

    private void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void showBanner() {
        cls();
        System.out.print(GREEN);
        System.out.println("############################");
        System.out.println("##                        ##");
        System.out.println("##  CONVERSOR DE MONEDAS  ##");
        System.out.println("##                        ##");
        System.out.println("############################");
        System.out.println("");
        System.out.print(RESET);
    }
    
    public void showMenu() {
        System.out.print(RED);
        System.out.println("########### MENU ############");
        System.out.println("1) Ingresar cantidad");
        System.out.println("2) Convertir a...");
        System.out.println("3) Cambiar moneda");
        System.out.println("4) Salir");
        System.out.println(RESET);

    }

    public void showCodes(int cols) {
        for (int i=0; i < codes.size(); i++) {
            System.out.printf("%-20s", codes.get(i));
            if ((i + 1) % cols == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    public void showStats() {
        System.out.println(WHITE + "MONEDA: " + GREEN + from + "\t" + WHITE + "CANTIDAD: " + GREEN + quantity);
        System.out.println(RESET);
    }

}