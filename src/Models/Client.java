package Models;

public class Client {

    private String clientNumber;
    private String name;
    private String email;
    private String phone;
    private int age;

    public Client(String clientNumber, String name, String email, String phone, int age) {
        setClientNumber(clientNumber);
        setName(name);
        setEmail(email);
        setPhone(phone);
        setAge(age);
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        if (clientNumber == null || clientNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Client number cannot be empty.");
        }
        this.clientNumber = clientNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().length() < 2) {
            throw new IllegalArgumentException("Name must be at least 2 characters long.");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email must contain '@'.");
        }
        int atIndex = email.indexOf("@");
        if (!email.substring(atIndex).contains(".")) {
            throw new IllegalArgumentException("Email must contain a dot ('.') after the '@' symbol.");
        }
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null || phone.length() < 8 || !phone.matches("\\d+")) {
            throw new IllegalArgumentException("Phone number must contain only digits and be at least 8 characters long.");
        }
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 18) {
            throw new IllegalArgumentException("Client must be at least 18 years old.");
        }
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("Client %s - Name: %s - Email: %s - Phone: %s - Age: %d",
                clientNumber, name, email, phone, age);
    }
}
