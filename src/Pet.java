/**
 * Represents a pet entity with specific details such as type, breed, and id.
 * This class extends the General_Information class to inherit general
 * personal information fields, allowing for an enriched representation of a pet.
 *
 * The class provides methods to access and modify the attributes relevant
 * to a pet, enabling flexible use within pet management systems or adoption processes.
 */
public class Pet extends General_Information {
    private String type, breed;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
