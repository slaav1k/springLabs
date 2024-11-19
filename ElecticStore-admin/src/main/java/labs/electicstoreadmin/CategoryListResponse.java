package labs.electicstoreadmin;


import java.util.List;

public class CategoryListResponse {

    private Embedded _embedded;

    public Embedded getEmbedded() {
        return _embedded;
    }

    public void setEmbedded(Embedded _embedded) {
        this._embedded = _embedded;
    }

    public static class Embedded {
        private List<Category> categories;

        public List<Category> getCategories() {
            return categories;
        }

        public void setCategories(List<Category> categories) {
            this.categories = categories;
        }
    }
}
