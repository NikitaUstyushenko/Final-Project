package com.epam.onlinepharmacy.factory;

import com.epam.onlinepharmacy.entity.User;
import com.epam.onlinepharmacy.main.UserRole;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class implements EntityFactory interface.
 *
 * @author Nikita
 * @version 1.0
 * @since 17.09.2018
 */
public final class UserFactory implements EntityFactory {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(UserFactory.class);
    }

    /**
     * Public default constructor.
     */
    public UserFactory() {
        // This constructor is intentionally empty.
        // Nothing special is needed here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User createEntity() {

        final User user = new User();
        final String debugString = " Object " + user + " created.";

        LOGGER.log(Level.DEBUG, debugString);

        return new User();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User createEntity(final HttpServletRequest request) {

        final User user = new User();
        final String debugString1 = " Attribute is null in method "
                + "createEntity(HttpServletRequest).";
        final String debugString2;

        if (request != null) {

            user.setUserName(request.getParameter("user_name"));
            user.setUserLastName(request.getParameter("user_lastname"));
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));
            user.setCash(0);

        } else {
            LOGGER.log(Level.DEBUG, debugString1);
        }

        debugString2 = " Object " + user + " created.";
        LOGGER.log(Level.DEBUG, debugString2);

        return user;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User createEntity(final ResultSet resultSet) {

        final User user = new User();
        final String debugString1
                = " Attribute is null in method createEntity(ResultSet).";
        final String debugString2 = " User role is incorrect.";
        final String debugString3;

        if (resultSet != null) {

            try {

                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("name"));
                user.setUserLastName(resultSet.getString("lastname"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));

                switch (resultSet.getString("role")) {
                    case "client":
                        user.setCash(resultSet
                                .getBigDecimal("cash").doubleValue());
                        break;
                    case "doctor":
                        user.setRole(UserRole.DOCTOR);
                        user.setSpeciality(resultSet
                                .getString("speciality"));
                        break;
                    case "admin":
                        user.setRole(UserRole.ADMIN);
                        break;

                    case "pharm":
                        user.setRole(UserRole.PHARMACIST);
                        break;
                    default:
                        LOGGER.log(Level.DEBUG, debugString2);
                        break;

                }
            } catch (SQLException e) {

                LOGGER.log(Level.DEBUG, debugString1);
                e.printStackTrace();

            }
        } else {
            LOGGER.log(Level.DEBUG, debugString1);
        }

        debugString3 = " Object " + user + " created.";
        LOGGER.log(Level.DEBUG, debugString3);

        return user;

    }

}
