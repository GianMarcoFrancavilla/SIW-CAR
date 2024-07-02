package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "licenseplate", unique = true)
    private String licensePlate;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Min(1920)
    @Max(2023)
    @Column(name = "yearofproduction")
    private int yearOfProduction;

    @Min(0)
    private int mileage;

    @NotBlank
    private String color;

    @Min(0)
    @NotNull
    private int price;

    private Boolean sold;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarImage> carImages;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private Review review;

    @ManyToOne
    private User owner;

    // Getters and Setters (omitted for brevity)

    @Override
    public int hashCode() {
        return Objects.hash(id, licensePlate, model, brand, yearOfProduction, mileage, color, price, sold, owner);
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public int getYearOfProduction() {
		return yearOfProduction;
	}

	public void setYearOfProduction(int yearOfProduction) {
		this.yearOfProduction = yearOfProduction;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Boolean getSold() {
		return sold;
	}

	public void setSold(Boolean sold) {
		this.sold = sold;
	}

	public List<CarImage> getCarImages() {
		return carImages;
	}

	public void setCarImages(List<CarImage> carImages) {
		this.carImages = carImages;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Car other = (Car) obj;
        return Objects.equals(id, other.id) && Objects.equals(licensePlate, other.licensePlate)
                && Objects.equals(model, other.model) && Objects.equals(brand, other.brand)
                && yearOfProduction == other.yearOfProduction && mileage == other.mileage
                && Objects.equals(color, other.color) && price == other.price && Objects.equals(sold, other.sold)
                && Objects.equals(owner, other.owner);
    }
}
