/**
 * 
 */
 
 document.getElementById('searchInput').addEventListener('keydown', function(event) {
          if (event.key === 'Enter') {
              event.preventDefault();
              const query = event.target.value;
              localStorage.setItem('searchQuery', query);
              window.location.href = 'product.jsp';
          }
      });