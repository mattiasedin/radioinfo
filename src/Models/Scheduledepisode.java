package Models;

import java.util.Date;

/**
 * Created by mattias on 1/11/17.
 */
@ApiModel(container = "schedule", pagination = true)
public class Scheduledepisode extends IconViewModel {

    private int episodeid;
    private String title;
    private Date starttimeutc;
    private Date endtimeutc;
    private Program program;
    private String imageurl;

    @Override
    public String getIconUrl() {
        return imageurl;
    }

    @TableDisplay(visible = false)
    public int getEpisodeid() {
        return episodeid;
    }

    @TableDisplay(visible = true, order = 1, name = "Title")
    public String getTitle() {
        return title;
    }

    @TableDisplay(visible = true, order = 2, name = "Start")
    public Date getStarttimeutc() {
        return starttimeutc;
    }

    @TableDisplay(visible = true, order = 3, name = "End")
    public Date getEndtimeutc() {
        return endtimeutc;
    }

    @TableDisplay(visible = false)
    public Program getProgram() {
        return program;
    }

    @TableDisplay(visible = false)
    public String getImageurl() {
        return imageurl;
    }

    @ApiModelData(type = ApiModelData.CONTENT_TYPES.innercontent, name = "episodeid")
    public void setEpisodeid(int episodeid) {
        this.episodeid = episodeid;
    }

    @ApiModelData(type = ApiModelData.CONTENT_TYPES.innercontent, name = "title")
    public void setTitle(String title) {
        this.title = title;
    }

    @ApiModelData(type = ApiModelData.CONTENT_TYPES.innercontent, name = "starttimeutc")
    public void setStarttimeutc(Date starttimeutc) {
        this.starttimeutc = starttimeutc;
    }

    @ApiModelData(type = ApiModelData.CONTENT_TYPES.innercontent, name = "endtimeutc")
    public void setEndtimeutc(Date endtimeutc) {
        this.endtimeutc = endtimeutc;
    }

    @ApiModelData(type = ApiModelData.CONTENT_TYPES.nestedObject, name = "program", nestedObjectType = Program.class)
    public void setProgram_id(Program program) {
        this.program = program;
    }

    @ApiModelData(type = ApiModelData.CONTENT_TYPES.innercontent, name = "imageurl")
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
