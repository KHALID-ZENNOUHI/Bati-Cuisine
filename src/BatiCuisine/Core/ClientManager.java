package BatiCuisine.Core;

import BatiCuisine.Domain.Entity.Client;
import BatiCuisine.Domain.Entity.Project;
import BatiCuisine.Service.Implementation.ClientServiceImpl;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientManager {
    private ClientServiceImpl clientService;
    private Scanner scanner;

    public ClientManager() throws SQLException {
        this.clientService = new ClientServiceImpl();
        this.scanner = new Scanner(System.in);
    }

    public String createClient() {
        System.out.print("Enter client name: ");
        String name = this.scanner.nextLine();
        System.out.print("Enter client address: ");
        String address = this.scanner.nextLine();
        System.out.print("Enter client phone number: ");
        String phoneNumber = this.scanner.nextLine();
        String isPro;
        do {
            System.out.print("Is the client a professional (y/n)? ");
            isPro = scanner.nextLine();
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
        System.out.print("Enter client name: ");
        String name = this.scanner.nextLine();
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

    public void updateClient () {
        System.out.print("Enter client name you want to update there info: ");
        String name = this.scanner.nextLine();
        Client client = clientService.findByName(name).orElse(null);
        if (client != null) {
            System.out.print("Enter the new name of client: ");
            String newName =  this.scanner.nextLine();
            System.out.print("Enter the new client address: ");
            String address = this.scanner.nextLine();
            System.out.print("Enter the new client phone number: ");
            String phoneNumber = this.scanner.nextLine();
            String isPro;
            do {
                System.out.print("Is the client a professional (y/n)? ");
                isPro = scanner.nextLine();
            } while (!isPro.equalsIgnoreCase("y") && !isPro.equalsIgnoreCase("n"));

            Boolean isProfessional = isPro.equalsIgnoreCase("y");
            Client newClient = new Client(name, address, phoneNumber, isProfessional);
            clientService.update(newClient);
            System.out.println("Client created successfully!");
        }
    }

    public void deleteClient () {
        System.out.print("Enter the name of the client you want to delete : ");
        String name = this.scanner.nextLine();
        Optional<Client> clientOptional = clientService.findByName(name);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            int id =  client.getId();
            Boolean isDeleted = clientService.delete(id);
            if (isDeleted) System.out.println("The client with the name" + name + "is deleted successfully!");
            else System.out.println("An error occurred on the delete of the client! ");
        } else {
            System.out.println("There is no client with the name: " + name);
        }
    }

    public void getClientProjects() {
        System.out.println("Enter the name of the client you want there project: ");
        String name  = this.scanner.nextLine();
        Optional<Client> clientOptional = clientService.findByName(name);
        if (clientOptional.isPresent()){
            Client  client = clientOptional.get();
            clientService.clientProjects(client.getId()).forEach(System.out::println);
        }
    }
}
