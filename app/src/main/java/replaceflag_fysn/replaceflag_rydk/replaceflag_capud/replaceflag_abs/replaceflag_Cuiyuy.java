package replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_abs;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class replaceflag_Cuiyuy {
    public static void updateUser(replaceflag_Pnnmjk item) {
        replaceflag_Mjrjrh db = new replaceflag_Mjrjrh();
        try {
            Dao<replaceflag_Pnnmjk, Long> dao = db.getDao(replaceflag_Pnnmjk.class);

            List<replaceflag_Pnnmjk> list = getUserList();
            if(list.size()>150){
                dao.delete(list.get(0));
            }

            dao.create(item);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }

    public static List<replaceflag_Pnnmjk> getUserList() {
        replaceflag_Mjrjrh db = new replaceflag_Mjrjrh();
        try {
            Dao<replaceflag_Pnnmjk, Long> dao = db.getDao(replaceflag_Pnnmjk.class);
            QueryBuilder<replaceflag_Pnnmjk, Long> builder = dao.queryBuilder();
//            builder.orderBy("stime", false);
            return dao.query(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
        return new ArrayList<replaceflag_Pnnmjk>();
    }

    public static int deleteUser(String mobile) {
        replaceflag_Mjrjrh db = new replaceflag_Mjrjrh();
        try {
            Dao<replaceflag_Pnnmjk, Long> dao = db.getDao(replaceflag_Pnnmjk.class);
            DeleteBuilder deleteBuilder = dao.deleteBuilder();
            deleteBuilder.where().eq("mobile", mobile);
            return deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
        return 0;
    }

}
