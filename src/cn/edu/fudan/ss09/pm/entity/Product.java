/**
 * 
 */
package cn.edu.fudan.ss09.pm.entity;

import java.sql.Date;

/**
 * @author Nicholas
 *
 */
/*==============================================================*/
/* Table: Product                                               */
/*==============================================================*/
/*create table Product
(
   ISBN                 numeric(13,0) not null,
   edition              int not null,
   impression           int not null,
   bookname             varchar(100) not null,
   author               varchar(30) not null,
   publishDate          date not null,
   impressionDate       date not null,
   press                varchar(100),
   primary key (ISBN, edition, impression)
);
*/
@SuppressWarnings("serial")
public class Product implements java.io.Serializable{
	private String ISBN;
	private int edition;
	private int impression;
	private String bookname;
	private String author;
	private Date publishDate;
	private Date impressionDate;
	private String press;
	
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(String isbn,int edition,int impression,String bookname,String author,Date publishDate,Date impressionDate,String press){
		this.ISBN=isbn;
		this.edition=edition;
		this.impression=impression;
		this.bookname=bookname;
		this.author=author;
		this.publishDate=publishDate;
		this.impressionDate=impressionDate;
		this.press=press;
	}

	public int getEdition() {
		return edition;
	}
	public void setEdition(int edition) {
		this.edition = edition;
	}
	public int getImpression() {
		return impression;
	}
	public void setImpression(int impression) {
		this.impression = impression;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public Date getImpressionDate() {
		return impressionDate;
	}
	public void setImpressionDate(Date impressionDate) {
		this.impressionDate = impressionDate;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ISBN == null) ? 0 : ISBN.hashCode());
		result = prime * result + edition;
		result = prime * result + impression;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (ISBN == null) {
			if (other.ISBN != null)
				return false;
		} else if (!ISBN.equals(other.ISBN))
			return false;
		if (edition != other.edition)
			return false;
		if (impression != other.impression)
			return false;
		return true;
	}

}
