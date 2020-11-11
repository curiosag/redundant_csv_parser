A long and complicated response to [this question](https://stackoverflow.com/questions/64774143/how-do-i-parse-this-matrix-in-java/64778176#64778176) on stack overflow.

How to get a double[][] from this wonky csv format `x x x, x x x, x x x, ..` where values are delimited by ` ` and rows by `, ` 

E.g. by writing one liners like `Stream.of(matrix.split(", ")).map(row -> Stream.of(row.split(" ")).map(Double::valueOf).toArray(Double[]::new)).toArray(Double[][]::new);`
Or typing and testing for ages a scanner and parser by hand, like done here.                           
