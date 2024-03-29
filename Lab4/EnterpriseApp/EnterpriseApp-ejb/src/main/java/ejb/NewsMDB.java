/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/MessageDrivenBean.java to edit this template
 */
package ejb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.ObjectMessage;
import jakarta.jms.TextMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author kowal
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/NewsQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")
})
public class NewsMDB implements MessageListener {

    @PersistenceContext
    private EntityManager em;

    public NewsMDB() {
    }

    @Override
    public void onMessage(Message message) {
        TextMessage textMsg = null;
        try {
            if (message instanceof TextMessage) {
                textMsg = (TextMessage) message;
                NewsItem e = new NewsItem();
                e.setFieldsFromConcatenatedText(textMsg.getText());
                em.persist(e);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
