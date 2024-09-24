package BatiCuisine.Core;

import BatiCuisine.Domain.Entity.Client;
import BatiCuisine.Domain.Entity.Project;
import BatiCuisine.Service.Implementation.ClientServiceImpl;
import BatiCuisine.Util.InputValidator;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientManager {
    private final ClientServiceImpl clientService;
    private final InputValidator validator;

    public ClientManager() throws SQLException {
        this.clientService = new ClientServiceImpl();
        this.validator = new InputValidator();
    }

    public String createClient() {
        String name = validator.validateString("Enter client name: ");

        String address = validator.validateString("Enter client address: ");

        String phoneNumber = validator.validateString("Enter client phone number: ");

        String isPro;
        do {
            isPro = validator.validateString("Is the client a professional (y/n)? ");
        } while (!isPro.equalsIgnoreCase("y") && !isPro.equalsIgnoreCase("n"));

        Boolean isProfessional = isPro.equalsIgnoreCase("y");

        Client client = new Client(name, address, phoneNumber, isProfessional);
        Client savedClient = clientService.save(client);

        if (savedClient != null) {
            System.out.println("Client created successfully with name: " + savedClient.getName());
            return savedClient.getName();
        } else {
            System.out.println("Error creating client.");
            return null;
        }
    }

    public String searchClient() {
        String name = validator.validateString("Enter client name: ");

        Optional<Client> clientOptional = clientService.findByName(name);

        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            System.out.println("Client found!\n" +
                    "Name: " + client.getName() + "\n" +
                    "Address: " + client.getAddress() + "\n" +
                    "Phone: " + client.getPhone());
            return client.getName();
        } else {
            System.out.println("Client not found.");
            return null;
        }
    }

    public void updateClient() {
        String name = validator.validateString("Enter the client name you want to update: ");

        Client client = clientService.findByName(name).orElse(null);

        if (client != null) {
            String newName = validator.validateString("Enter the new name of client: ");
            String address = validator.validateString("Enter the new client address: ");
            String phoneNumber = validator.validateString("Enter the new client phone number: ");

            String isPro;
            do {
                isPro = validator.validateString("Is the client a professional (y/n)? ");
            } while (!isPro.equalsIgnoreCase("y") && !isPro.equalsIgnoreCase("n"));

            Boolean isProfessional = isPro.equalsIgnoreCase("y");

            Client newClient = new Client(newName, address, phoneNumber, isProfessional);
            clientService.update(newClient);

            System.out.println("Client updated successfully!");
        } else {
            System.out.println("Client not found.");
        }
    }

    public void deleteClient() {
        String name = validator.validateString("Enter the name of the client you want to delete: ");

        Optional<Client> clientOptional = clientService.findByName(name);

        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            int id = client.getId();

            Boolean isDeleted = clientService.delete(id);

            if (isDeleted) {
                System.out.println("The client with the name " + name + " is deleted successfully!");
            } else {
                System.out.println("An error occurred while deleting the client.");
            }
        } else {
            System.out.println("There is no client with the name: " + name);
        }
    }

    public void getClientProjects() {
        String name = validator.validateString("Enter the name of the client to view their projects: ");

        Optional<Client> clientOptional = clientService.findByName(name);

        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();

            clientService.clientProjects(client.getId()).forEach(System.out::println);
        } else {
            System.out.println("Client not found.");
        }
    }



}
