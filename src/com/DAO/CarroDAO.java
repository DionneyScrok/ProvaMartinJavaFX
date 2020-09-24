package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Entity.Carro;
import com.db.ConexaoHSQLDB;

public class CarroDAO extends ConexaoHSQLDB {
	final String Insert_SQL = " INSERT INTO CARRO (marca, modelo, ano, valor ) VALUES (?, ?, ?, ? ) ";
	final String SQL_SELECT = " SELECT * FROM CARRO ";
	final String SQL_SELECT_CARRO_ID = " SELECT * FROM CARRO WHERE ID =? ";
	final String SQL_ALTERA_CARRO = " UPDATE CARRO SET MARCA = ?, MODELO =?, ANO =?, VALOR =? WHERE ID =? ";
	final String SQL_DELETA_CARRO = "DELETE FROM CARRO WHERE ID = ?";

	public int inserirCarro(Carro carro) {

		int quantidade = 0;
		try (Connection connection = this.connectar();
				PreparedStatement pst = connection.prepareStatement(Insert_SQL);) {
			pst.setString(1, carro.getMarca());
			pst.setString(2, carro.getModelo());
			pst.setInt(3, carro.getAno());
			pst.setFloat(4, carro.getValor());
			quantidade = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return quantidade;
	}
	public List<Carro> listAll() {
		List<Carro> listaCarros = new ArrayList<Carro>();
		try (Connection connection = this.connectar();
				PreparedStatement pst = connection.prepareStatement(SQL_SELECT);) {
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Carro carro = new Carro();
				carro.setId(rs.getInt("ID"));
				carro.setMarca(rs.getString("MARCA"));
				carro.setModelo(rs.getString("MODELO"));
				carro.setAno(rs.getInt("Ano"));
				carro.setValor(rs.getFloat("VALOR"));

				listaCarros.add(carro);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaCarros;
	}
	public Carro findByID(int id) {
		Carro carro = null;
		try (Connection connection = this.connectar();
				PreparedStatement pst = connection.prepareStatement(SQL_SELECT_CARRO_ID);) {

			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				carro = new Carro();
				carro.setId(rs.getInt("ID"));
				carro.setMarca(rs.getString("MARCA"));
				carro.setModelo(rs.getString("MODELO"));
				carro.setAno(rs.getInt("ANO"));
				carro.setValor(rs.getFloat("VALOR"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return carro;
	}
	
	
	public int alterar(Carro carro) {
		int quantidade = 0;
		try (Connection connection = this.connectar();
				PreparedStatement pst = connection.prepareStatement(SQL_ALTERA_CARRO);) {
			pst.setString(1, carro.getMarca());
			pst.setString(2, carro.getModelo());
			pst.setInt(3, carro.getAno());
			pst.setFloat(4, carro.getValor());
			pst.setInt(5, carro.getId());
			quantidade = pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return quantidade;
	}
	
	public int deletar(int id) {
		int quantidade = 0;
		try (Connection connection = this.connectar();
				PreparedStatement pst = connection.prepareStatement(SQL_DELETA_CARRO);) {
			pst.setInt(1, id);
			quantidade = pst.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return quantidade;
	}
	
	

	

}
