package model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Caixa {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
     
    private double valor;
          
    @Temporal(TemporalType.DATE)
    private Date data_pagamento;

    public Caixa() {
    }

    public Caixa(double valor, Date data_pagamento) {
        this.valor = valor;
        this.data_pagamento = data_pagamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getData_pagamento() {
        return data_pagamento;
    }

    public void setData_pagamento(Date data_pagamento) {
        this.data_pagamento = data_pagamento;
    }
    
    
    
}
