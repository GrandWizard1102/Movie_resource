import java.io.*;
import java.net.*;
import java.sql.*;

public class Server {
    public static void main(String[] args) throws IOException, SQLException {
        System.out.println(System.getProperty("java.class.path"));

        try (ServerSocket serverSocket = new ServerSocket(54323)) {
            System.out.println("Server started, waiting for client connection...");

            while (true) {
                
                Socket socket = serverSocket.accept();
                System.out.println("Client connected.");
                
                
                new ClientHandler(socket).start();
            }
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
             
            // Database connection setup
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            String username = "system";
            String password = "kavi2006";
            try (Connection con = DriverManager.getConnection(url, username, password)) {
                while (true) {
                    String task = in.readUTF();
                    char ch = task.charAt(0);
                    
                    switch (ch) {
                        case 'h':
                            handleTopMovies(con, out);
                            break;
                        case 'l':
                            handleLogin(con, in, out);
                            break;
                        case 's':
                            handleSignup(con, in, out);
                            break;
                        case 'r':
                            handleMovieDetails(con, in, out);
                            break;
                        case 'n':
                            handlerating(con, in, out);
                            break;
                        default:
                            out.writeUTF("Invalid task.");
                            break;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close(); // Ensure the socket is closed properly
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Handle task 'h': Fetch top 5 movies
    private void handleTopMovies(Connection con, DataOutputStream out) throws SQLException, IOException {
        String sql0 = "SELECT * FROM Movies m JOIN link l ON m.movie_id = l.movie_id ORDER BY Rating DESC FETCH FIRST 5 ROWS ONLY";
        try (Statement st0 = con.createStatement(); ResultSet r0 = st0.executeQuery(sql0)) {
            while (r0.next()) {
                out.writeUTF(r0.getString("Title"));
                out.flush();
                out.writeUTF(r0.getString("link"));
                out.flush();
            }
        }
    }

    // Handle task 'l': User login
    private void handleLogin(Connection con, DataInputStream in, DataOutputStream out) throws IOException, SQLException {
        String y = in.readUTF(); // Receive as String
        String n = in.readUTF();
        System.out.println("Received Username: " + n); 
        System.out.println("Received Password: " + y);

        String sql = "SELECT * FROM users WHERE Password = ? AND username = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, y);
            st.setString(2, n);
            try (ResultSet r = st.executeQuery()) {
                if (!r.next()) {
                    out.writeUTF("The given details are wrong");
                } else {
                    System.out.println("Sending Username: " + n);
                    out.writeUTF(n);
                }
            }
        }
    }

    // Handle task 's': User signup
    private void handleSignup(Connection con, DataInputStream in, DataOutputStream out) throws IOException, SQLException {
        String x = in.readUTF();
        String t = in.readUTF();
        String u = in.readUTF();

        String checksql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement cst = con.prepareStatement(checksql)) {
            cst.setString(1, x);
            try (ResultSet r2 = cst.executeQuery()) {
                if (r2.next()) {
                    out.writeUTF("The given user already exists!");
                } else {
                    String sql2 = "INSERT INTO users(user_id, username, email, Password, Date_joined) VALUES(user_seq.NEXTVAL, ?, ?, ?, SYSDATE)";
                    try (PreparedStatement st2 = con.prepareStatement(sql2)) {
                        st2.setString(1, t);
                        st2.setString(2, x);
                        st2.setString(3, u);
                        int t1 = st2.executeUpdate();
                        if (t1 > 0) {
                            out.writeUTF("Registered successfully!!");
                            out.flush();
                            out.writeUTF(t);
                            out.flush();
                        }
                    }
                }
            }
        }
    }

    // Handle task 'r': Movie details
    private void handleMovieDetails(Connection con, DataInputStream in, DataOutputStream out) throws IOException, SQLException {
        String moviename = in.readUTF();
        String sql3 = "SELECT m.movie_id, m.title, m.release_year, m.duration, m.genre, m.rating AS movie_rating, m.language, " +
                      "a.name AS actor_name, ma.role AS ROLE, a.gender AS actor_gender, d.name AS director_name, " +
                      "r.rating_value AS rating_score, r.review AS rating_review, ml.link AS movie_link " +
                      "FROM Movies m LEFT JOIN Movie_Actors ma ON m.movie_id = ma.movie_id LEFT JOIN Actors a ON ma.actor_id = a.actor_id " +
                      "LEFT JOIN Movie_Directors md ON m.movie_id = md.movie_id LEFT JOIN Directors d ON md.director_id = d.director_id " +
                      "LEFT JOIN Ratings r ON m.movie_id = r.movie_id LEFT JOIN link ml ON m.movie_id = ml.movie_id WHERE m.title = ?";
        try (PreparedStatement st3 = con.prepareStatement(sql3)) {
            st3.setString(1, moviename);
            try (ResultSet r3 = st3.executeQuery()) {
                if (!r3.next()) {
                    out.writeUTF("No such Movie found...Try Another!");
                    out.writeUTF("end");
                } else {
                    do {
                        out.writeUTF(r3.getString("Title"));
                        out.writeUTF(r3.getString("movie_link"));
                        out.writeUTF(r3.getString("RATING_REVIEW"));
                        out.writeUTF(r3.getString("DIRECTOR_NAME"));
                        out.writeUTF(r3.getString("DURATION"));
                        out.writeUTF(r3.getString("RELEASE_YEAR"));
                        out.writeUTF(r3.getString("Genre"));
                        out.writeUTF(r3.getString("ACTOR_NAME"));
                        out.writeUTF(r3.getString("ROLE"));
                        out.writeUTF(r3.getString("RATING_SCORE"));
                    } while (r3.next());
                    out.writeUTF("end");
                }
            }
        }
    }

    private void handlerating(Connection con,DataInputStream in,DataOutputStream out) throws SQLException, IOException {
        int newRateVal= in.readInt();
        String title=in.readUTF();
        String login=in.readUTF();
        PreparedStatement st = null, st2 = null;
        ResultSet r = null;
        
        System.out.println(newRateVal);
        // Insert new rating
        String sql2 = "INSERT INTO Updated_Ratings (movie_id, new_rating, user_id) " + 
                      "VALUES ( " + 
                      "(SELECT movie_id FROM Movies WHERE title = ?), " + 
                      "?, " + 
                      "(SELECT user_id FROM Users WHERE username = ?) " + 
                      ")";
        st = con.prepareStatement(sql2);
        st.setString(1, title);
        st.setDouble(2, newRateVal/10.0);
        st.setString(3, login);
        int rowsAffected = st.executeUpdate();
        out.writeInt(rowsAffected);
        if (rowsAffected > 0) {

            // Fetch the updated rating from the Movies table
            String sql3 = "Select * from Ratings where Movie_ID=(select Movie_ID from Movies where Title=(?))";
            st2 = con.prepareStatement(sql3);
            st2.setString(1, title);
            r = st2.executeQuery();

            // Update the JLabel with the new rating
            if (r.next()) {
                out.writeUTF("true");
                out.writeUTF(r.getString("Rating_value"));
            }
        }
    }
}
