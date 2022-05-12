package replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_abs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Ahrtry;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class replaceflag_Mjrjrh extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "keep";
    private static final int DATABASE_VERSION = 1;

    public replaceflag_Mjrjrh(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public replaceflag_Mjrjrh() {
        super(replaceflag_Ahrtry.getApplication(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        //onCreate的方法只有在第一次创建数据库中才调用
        try {
            TableUtils.createTable(connectionSource, replaceflag_Pnnmjk.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        logger.error("数据库升级");
    }

    /**
     * 升级数据库时增加字段
     *
     * @param db
     * @param tableName
     * @param columnName
     * @param columnType
     * @param defaultField
     */
    public synchronized void updateColumn(SQLiteDatabase db, String tableName,
                                          String columnName, String columnType, Object defaultField) {
        try {
            if (db != null) {
                Cursor c = db.rawQuery("SELECT * from " + tableName
                        + " limit 1 ", null);
                boolean flag = false;

                if (c != null) {
                    for (int i = 0; i < c.getColumnCount(); i++) {
                        if (columnName.equalsIgnoreCase(c.getColumnName(i))) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag == false) {
                        String sql = "alter table " + tableName + " add "
                                + columnName + " " + columnType + " default "
                                + defaultField;
                        db.execSQL(sql);
                    }
                    c.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
