package oladejo.mubarak.NiqueResortHub.data.repository;

import oladejo.mubarak.NiqueResortHub.data.model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
}
