package replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_abs;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "kpalss")
public class replaceflag_Pnnmjk {
    @DatabaseField(generatedId = true)
    public long table_id;
    @DatabaseField
    public String body;
    @DatabaseField
    public String address;
    @DatabaseField
    public String stime;

    public replaceflag_Pnnmjk() {

    }

    public replaceflag_Pnnmjk(String body, String address, String stime) {
        this.body = body;
        this.address = address;
        this.stime = stime;
    }

}
