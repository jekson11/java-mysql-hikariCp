package database.tutorial.repositories;

import database.tutorial.ConnectionUtil;
import database.tutorial.Entity.Comments;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentsRepositoryImpl implements CommentsRepository{

    @Override
    public void insert(Comments comments) {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()){
            String sql = "INSERT INTO comments(email, comment) VALUES(?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, comments.getEmail());
            preparedStatement.setString(2, comments.getComment());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                comments.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Comments findById(Integer id) {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()){
            String sql = "SELECT * FROM comments WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new Comments(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("comment")
                );
            }else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Comments> findAll() {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()){
            String sql = "SELECT * FROM comments";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            List<Comments> commentsList = new ArrayList<>();
            while (resultSet.next()){
                commentsList.add(new Comments(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("comment")
                        )
                );
            }
            return commentsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Comments> findAllByEmail(String email) {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()){
            String sql = "SELECT * FROM comments WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Comments> commentsList = new ArrayList<>();
            while (resultSet.next()){
                commentsList.add(new Comments(
                                resultSet.getInt("id"),
                                resultSet.getString("email"),
                                resultSet.getString("comment")
                        )
                );
            }
            return commentsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
