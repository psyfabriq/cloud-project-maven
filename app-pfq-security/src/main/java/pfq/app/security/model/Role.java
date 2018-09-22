package pfq.app.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@Enumerated(EnumType.STRING)
	@NaturalId
	@Column(name="name",columnDefinition = "VARCHAR(60)")
	private String name;
	
	@Column(name = "notdeleted",columnDefinition = "INT(1)")
	boolean notdeleted;

	public Role() {

	}

	public Role(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String roleName) {
		this.name = roleName;
	}

	public boolean isNotdeleted() {
		return notdeleted;
	}

	public void setNotdeleted(boolean notdeleted) {
		this.notdeleted = notdeleted;
	}
	
	

}