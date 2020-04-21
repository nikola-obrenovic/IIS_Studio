package ui.events;

public interface IDdActionListener {
    public boolean BeforeInsert();
    public void AfterInsert(boolean status, String tob_id);
    public boolean BeforeUpdate();
    public void AfterUpdate(boolean status, String tob_id);
    public boolean BeforeDelete();
    public void AfterDelete(boolean status, String tob_id);
}
