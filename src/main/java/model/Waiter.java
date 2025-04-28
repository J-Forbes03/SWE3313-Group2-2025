package model;

/**
 * Represents a waiter in the restaurant system.
 * Contains basic information like ID, name, and password.
 */
public class Waiter {

    private int id;
    private String name;
    private String password;

    /**
     * Constructs a Waiter with specified ID, name, and password.
     *
     * @param id       The unique identifier for the waiter.
     * @param name     The name of the waiter.
     * @param password The waiter's password for login authentication.
     */
    public Waiter(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    /**
     * Returns the waiter's ID.
     *
     * @return The waiter's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the waiter's name.
     *
     * @return The waiter's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the waiter's password.
     *
     * @return The waiter's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns a string representation of the waiter object.
     *
     * @return A string containing waiter ID, name, and password.
     */
    @Override
    public String toString() {
        return "Waiter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
