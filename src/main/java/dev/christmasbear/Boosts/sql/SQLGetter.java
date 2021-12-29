/*package dev.christmasbear.Boosts.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;

import dev.christmasbear.Boosts.Boosts;

public class SQLGetter {
	private Boosts plugin;
	
	public SQLGetter(Boosts plugin) {
		this.plugin = plugin;
	}
	
	public void createTable() {
		PreparedStatement ps;
		try {
			ps = plugin.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS pollux "
					+ "(name varchar(100),uuid varchar(100),coins int(100),gems int(100),current_kit varchar(100),primary key (name))");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createPlayer(Player p) {
		try {
			UUID uuid = p.getUniqueId();
			if (!exists(uuid)) {
				PreparedStatement ps2 = plugin.sql.getConnection().prepareStatement("INSERT IGNORE INTO pollux (NAME,UUID) VALUES (?,?)");
				ps2.setString(1, p.getName());
				ps2.setString(2, uuid.toString());
				ps2.executeUpdate();
				
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean exists(UUID uuid) {
		try {
			PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT * FROM pollux WHERE UUID=?");
			ps.setString(1, uuid.toString());
			
			ResultSet results = ps.executeQuery();
			if (results.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void addCoins(UUID uuid, int coins) {
		try {
			PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE pollux set coins=? WHERE UUID=?");
			ps.setInt(1, (getCoins(uuid) + coins));
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getCoins(UUID uuid) {
		try {
			PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT coins FROM pollux WHERE UUID=?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			int coins = 0;
			if (rs.next()) {
				coins = rs.getInt("COINS");
				return coins;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void addGems(UUID uuid, int gems) {
		try {
			PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE pollux set gems=? WHERE UUID=?");
			ps.setInt(1, (getGems(uuid) + gems));
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getGems(UUID uuid) {
		try {
			PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT gems FROM pollux WHERE UUID=?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			int coins = 0;
			if (rs.next()) {
				coins = rs.getInt("GEMS");
				return coins;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void setKit(UUID uuid, String kitID) {
		try {
			PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE pollux set current_kit=? WHERE UUID=?");
			ps.setString(1, kitID);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getKit(UUID uuid) {
		try {
			PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT current_kit FROM pollux WHERE UUID=?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			String kit = "";
			if (rs.next()) {
				kit = rs.getString("current_kit");
				return kit;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
}
*/