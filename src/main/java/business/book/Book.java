package business.book;

public class Book {

    Long bookId;
    String title;
    String author;
    Integer price;
    Boolean isPublic;
    Long categoryId;

    public Book(Long bookId,
                String title,
                String author,
                Integer price,
                Boolean isPublic,
                Long categoryId) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
        this.isPublic = isPublic;
        this.categoryId = categoryId;


    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
