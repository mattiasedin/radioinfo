package Models;

/**
 * Created by mattias on 1/18/17.
 * <p>
 * Pagination class for the SR API. This class represent the the pagination behaviour of the api. Some api calls is
 * divided in mutliple calls described in this class.
 */
@ApiModel(container = "pagination")
public class Pagination {
    private int page;
    private int size;
    private String nextpage;
    private int totalpages;
    private int totalhits;

    /**
     * Check if current pagination has a next page.
     * @return true if there exist a next page, else false.
     */
    public boolean hasNextPage() {
        return page < totalpages;
    }


    @ApiModelData(type = ApiModelData.CONTENT_TYPES.innercontent, name = "page")
    public void setPage(int page) {
        this.page = page;
    }

    @ApiModelData(type = ApiModelData.CONTENT_TYPES.innercontent, name = "size")
    public void setSize(int size) {
        this.size = size;
    }

    @ApiModelData(type = ApiModelData.CONTENT_TYPES.innercontent, name = "nextpage")
    public void setNextpage(String nextpage) {
        this.nextpage = nextpage;
    }

    @ApiModelData(type = ApiModelData.CONTENT_TYPES.innercontent, name = "totalpages")
    public void setTotalpages(int totalpages) {
        this.totalpages = totalpages;
    }

    @ApiModelData(type = ApiModelData.CONTENT_TYPES.innercontent, name = "totalhits")
    public void setTotalhits(int totalhits) {
        this.totalhits = totalhits;
    }


    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public String getNextpage() {
        return nextpage;
    }

    public int getTotalpages() {
        return totalpages;
    }

    public int getTotalhits() {
        return totalhits;
    }
}
