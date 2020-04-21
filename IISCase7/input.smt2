(declare-fun A () Int)

(assert (> A 2))
(assert (not ( distinct  A 1)))

(check-sat)
