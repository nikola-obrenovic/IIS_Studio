(declare-fun MajEmpNum () Real)
(declare-fun FacId () Real)
(declare-fun FacEmpNum () Real)

(assert (> FacEmpNum MajEmpNum))
(assert (not (= FacId 100)))

(check-sat)
