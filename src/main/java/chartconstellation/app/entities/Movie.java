package chartconstellation.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="movie")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name="movieName")
	private String movieName;
	
	@Column(name="printType")
	private String printType;
	
	@Column(name="price")
	private int price;

	public String getMovieName() {
		return movieName;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", movieName=" + movieName + ", printType=" + printType + ", price=" + price + "]";
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}


	public String getPrintType() {
		return printType;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Movie(long id, String movieName, String printType, int price) {
		super();
		this.id = id;
		this.movieName = movieName;
		this.printType = printType;
		this.price = price;
	}

	protected Movie() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	

}
